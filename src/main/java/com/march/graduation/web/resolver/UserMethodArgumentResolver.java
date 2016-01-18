package com.march.graduation.web.resolver;

import com.march.graduation.web.interceptor.LoginInterceptor;
import com.march.graduation.web.model.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (UserInfo.class.isAssignableFrom(parameter.getParameterType())) {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            if (request == null) {
                return null;
            }

            UserInfo user = (UserInfo) request.getAttribute(LoginInterceptor.USER_ATTRIBUTE_KEY);
            return user;
        }
        return null;
    }

}