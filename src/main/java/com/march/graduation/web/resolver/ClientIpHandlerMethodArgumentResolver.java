package com.march.graduation.web.resolver;

import com.march.graduation.utils.IPUtils;
import com.march.graduation.web.annotation.ClientIp;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class ClientIpHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        ClientIp clientIp = parameter.getParameterAnnotation(ClientIp.class);
        if(clientIp == null){
            return false;
        }else if (parameter.getParameterType().equals(String.class)){
            return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return IPUtils.findRealIPFromRequest(webRequest.getNativeRequest(HttpServletRequest.class));
    }

}
