package com.march.graduation.view;

import com.march.graduation.web.model.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class JsonAndViewMethodReturnValueHandler implements HandlerMethodReturnValueHandler, InitializingBean {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonAndViewMethodReturnValueHandler.class);

    private static final String DEFAULT_VIEW_NAME = "jsonView";
    
    public static final String JAV_MODEL_KEY = JsonAndView.class.getName() + "-instance";

    private List<String> customResultCodeList;

    private Map<Integer, FileAndName> codeNameMap = new HashMap<Integer, FileAndName>();

    private String viewName = DEFAULT_VIEW_NAME;

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodReturnValueHandler#supportsReturnType(org.springframework.core.MethodParameter)
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return JsonAndView.class.isAssignableFrom(returnType.getParameterType());
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodReturnValueHandler#handleReturnValue(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest)
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            return;
        }

        mavContainer.setViewName(viewName);

        if (!(returnValue instanceof JsonAndView)) {
            // should not happen
            throw new UnsupportedOperationException("Unexpected return type: "
                    + returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }
        JsonAndView jav = (JsonAndView) returnValue;
        
        if(jav.getErrcode() != ResultCode.OK){
            jav.setRet(false);
        }
        mavContainer.getModel().clear();
        mavContainer.addAttribute(JAV_MODEL_KEY, jav);

        switch (jav.getErrcode()) {
            case ResultCode.STD_OK:
            case ResultCode.STD_CREATED:
            case ResultCode.STD_FORBIDDEN:
            case ResultCode.STD_NOT_FOUND:
            case ResultCode.STD_UNAUTH:
            case ResultCode.STD_UNSUPPORTED_MEDIA_TYPE:
                HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
                if (response != null) {
                    response.setStatus(jav.getErrcode());
                }
        }
        if (StringUtils.isNotBlank(jav.getErrmsg())) {
            return;
        }
        FileAndName fileAndName = codeNameMap.get(jav.getErrcode());
        if (fileAndName != null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(fileAndName.getFile(),
                    LocaleContextHolder.getLocale());
            String msg = null;
            try {
                msg = resourceBundle.getString(fileAndName.getName());
            } catch (MissingResourceException e) {
                logger.warn("errmsg [" + fileAndName.getName() + "(" + jav.getErrcode() + ") is not defined in "
                        + fileAndName.getFile() + ".properties");
            }
            if (msg != null) {
                jav.setErrmsg(msg);
            } else {
                jav.setErrmsg(fileAndName.getFile() + " " + fileAndName.getName() + " (" + jav.getErrcode() + ")");
            }
        } else {
            jav.setErrmsg("unknown code (" + jav.getErrcode() + ")");
        }
    }

    public void setCustomResultCodes(List<String> customResultCodeList) {
        this.customResultCodeList = customResultCodeList;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadCode(ResultCode.class);
        if (CollectionUtils.isNotEmpty(customResultCodeList)) {
            for (String customClassName : customResultCodeList) {
                Class<?> clazz = loadCodeClass(customClassName);
                if (clazz != null) {
                    loadCode(clazz);
                }
            }
        }
    }

    private Class<?> loadCodeClass(String name) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(name);
    }

    private void loadCode(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
        String fileName = clazz.getSimpleName();
        fileName = Character.toLowerCase(fileName.charAt(0)) + fileName.substring(1);
        if (ResourceBundle.getBundle("message/" + fileName) != null) {
            fileName = "message/" + fileName;
        }
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.getType() == int.class && Modifier.isStatic(field.getModifiers())
                    && Modifier.isFinal(field.getModifiers())) {
                int code = field.getInt(null);
                String name = field.getName();
                if (codeNameMap.containsKey(code)) {
                    FileAndName value = codeNameMap.get(code);
                    throw new IllegalStateException("duplicate result code [" + code + "] defined in " + fileName + "."
                            + name + " and " + value.getFile() + "." + value.getName());
                }
                codeNameMap.put(code, new FileAndName(fileName, name));
            }
        }
    }

    class FileAndName {

        private String name;

        private String file;

        public FileAndName(String file, String name) {
            this.file = file;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public String getFile() {
            return this.file;
        }
    }

}
