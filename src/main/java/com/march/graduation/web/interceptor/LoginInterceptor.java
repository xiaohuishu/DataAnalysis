package com.march.graduation.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.march.graduation.utils.ServletUtils;
import com.march.graduation.view.JsonAndView;
import com.march.graduation.web.annotation.LoginRequired;
import com.march.graduation.web.model.ResultCode;
import com.march.graduation.web.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    public final static String USER_ATTRIBUTE_KEY = "loginUser";

    private final static String LOGIN_URL = "http://localhost:8080/user/login";

    private final static String CSRF_TOKEN_NAME = "csrfToken";

    private final static String DEFAULT_CALLBACK_PARAM = "callback";

    private List<String> excludedUrls;

    private String csrfTokenErrResponse;

    private ObjectMapper objectMapper = new ObjectMapper();

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (CollectionUtils.isNotEmpty(excludedUrls)) {
            String requestUri = request.getRequestURI();
            for (String url : excludedUrls) {
                if (requestUri.contains(url)) {
                    return true;
                }
            }
        }

        if (!(handler instanceof HandlerMethod)) {
            throw new IllegalArgumentException("LoginInterceptor only support HanlderMethod handler");
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        UserInfo userInfo = new UserInfo();

        /*if (user != null && !user.isExpired()) {
            userInfo = createUserInfo(user);
            request.setAttribute(USER_ATTRIBUTE_KEY, userInfo);
        }*/

        String csrfTokenCookie = ServletUtils.getCookieValue(request, CSRF_TOKEN_NAME);

        /**
         * 登陆账号验证
         */
        LoginRequired anno = null;
        if (handlerMethod.getMethod().isAnnotationPresent(LoginRequired.class)) {
            anno = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        } else if (handlerMethod.getBeanType().isAnnotationPresent(LoginRequired.class)) {
            anno = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
        }

        do {
            /**
             * 无登陆要求
             */
            if (null == anno) {
                return true;
            }

            /**
             * check csrfToken
             */
            if (anno.checkCsrfToken()) {
                String csrfTokenParam = request.getParameter(CSRF_TOKEN_NAME);
                if (StringUtils.isBlank(csrfTokenCookie) || !StringUtils.equals(csrfTokenCookie, csrfTokenParam)) {
                    break;
                }
            }
            return true;
        } while (false);

        if (JsonAndView.class.isAssignableFrom(handlerMethod.getMethod().getReturnType())
                || handlerMethod.getMethod().getName().endsWith("Json")) {
            /**
             * 弹框
             */
            String msg;
            msg = this.csrfTokenErrResponse;
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            // 如果是JSONP请求，返回JSONP结果
            if ("GET".equals(request.getMethod().toUpperCase())) {
                String callback = request.getParameter(DEFAULT_CALLBACK_PARAM);
                if (StringUtils.isNotBlank(callback)) {
                    response.getOutputStream().print(callback + "(");
                    response.getOutputStream().print(msg);
                    response.getOutputStream().print(");");

                    return false;
                }
            }
            response.getWriter().write(msg);
            return false;
        }

        response.sendRedirect(LOGIN_URL);
        return false;
    }


    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        JsonAndView jav = new JsonAndView();
        jav.setRet(false);
        jav.setErrcode(ResultCode.LOGIN_REQUIRED);
        jav.setErrmsg("login required");
        jav.setErrmsg("csrf token error");
        csrfTokenErrResponse = objectMapper.writeValueAsString(jav);
    }

    /**
     * @param excludedUrls the excludedUrls to set
     */
    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}
