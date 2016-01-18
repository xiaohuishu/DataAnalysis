package com.march.graduation.view;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonpView extends MappingJackson2JsonView {

    private final static String DEFAULT_CALLBACK_PARAM = "callback";

    private String callbackParam = DEFAULT_CALLBACK_PARAM;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("GET".equals(request.getMethod().toUpperCase())) {
            String callback = request.getParameter(callbackParam);
            if (StringUtils.isBlank(callback)) {
                super.render(model, request, response);
            } else {
                response.getOutputStream().print(callback + "(");
                super.render(model, request, response);
                response.getOutputStream().print(");");
            }
        } else {
            super.render(model, request, response);
        }
    }

    public String getCallbackParam() {
        return callbackParam;
    }

    public void setCallbackParam(String callbackParam) {
        this.callbackParam = callbackParam;
    }

}
