package com.march.graduation.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServletUtils {

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        String val = null;
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    val = cookie.getValue();
                    break;
                }
            }
        }
        return val;
    }

    /**
     * 获取客户端ua
     * 
     * @param request
     * @return
     */
    public static final String getUserAgent(HttpServletRequest request) {
        if (request != null && request.getHeader("User-Agent") != null) {
            return request.getHeader("User-Agent");
        }
        return "";
    }

    public static final Map<String, String> getRequestParamMap(HttpServletRequest request) {
        Map<String, String> requestParamsMap = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String param = names.nextElement();
            String value = StringUtils.substring(request.getParameter(param), 0, 100);
            requestParamsMap.put(param, value);
        }
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> mms = multipartRequest.getFileMap();
            for (String key : mms.keySet()) {
                requestParamsMap.put(key, mms.get(key).getOriginalFilename());
            }
        }
        return requestParamsMap;
    }

    public static final String getRequestRefer(HttpServletRequest request) {
        String refer = "";
        @SuppressWarnings("unchecked")
        Enumeration<String> e = request.getHeaders("Referer");
        while (e.hasMoreElements()) {
            refer = e.nextElement();
        }
        return refer;
    }

    /**
     * 从给定url抽取域名
     * <p>
     * eg http[s]://www.baidu.com retrun baidu.com
     * <p>
     * http://m5.baidu.com return m5.baidu.com
     * 
     * @param url
     * @return
     */
    public static final String extractHost(String url) {
        if (StringUtils.isBlank(url)) {
            return StringUtils.EMPTY;
        }
        String lowUrl = url.toLowerCase();
        int start = -1;
        if (lowUrl.startsWith("http://")) {
            start = 7;
        } else if (lowUrl.startsWith("https://")) {
            start = 8;
        }
        if (start < 0) {
            return StringUtils.EMPTY;
        }
        int i = start;
        for (; i < lowUrl.length(); i++) {
            char c = lowUrl.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '.') {
                continue;
            }
            break;
        }
        if (i > start) {
            return url.substring(start, i).replace("www.", StringUtils.EMPTY);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 从指定域名抽取一级域名
     * <p>
     * eg m5.baidu.com return baidu.com
     * 
     * @param host
     * @return
     */
    public static final String extractMainHost(String host) {
        if (StringUtils.isBlank(host)) {
            return StringUtils.EMPTY;
        }
        int index1 = host.indexOf('.');
        if (index1 == -1) {
            return StringUtils.EMPTY;
        }
        String level1Host = host;
        int index2 = host.lastIndexOf('.');
        if (index1 != index2) {
            int index3 = host.lastIndexOf('.', index2 - 1);
            level1Host = host.substring(index3 + 1);
        }
        return level1Host;
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = request.getReader();
            if (null != bufferedReader) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}