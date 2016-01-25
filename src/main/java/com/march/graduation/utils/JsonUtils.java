package com.march.graduation.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();// can reuse, share globally

    static {
        jsonObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonObjectMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    /**
     * 用jackson的安全方法转换，包括数组等
     * 
     * @param json
     * @param t
     * @return
     * @throws IOException
     */

    public static <T> T toTypeSafe(String json, TypeReference<T> t) throws IOException {
        if (json == null || json.length() == 0) {
            return (T) null;
        }
        return (T) jsonObjectMapper.readValue(getSafeJson(json), t);
    }

    /**
     * 转换jsonnode节点
     * 
     * @param jsonNode
     * @param t
     * @return
     * @throws IOException
     */
    public static <T> T toTypeSafe(JsonNode jsonNode, TypeReference<T> t) throws IOException {
        if (jsonNode == null) {
            return (T) null;
        }
        return (T) jsonObjectMapper.readValue(jsonNode, t);
    }

    public static <T> T toTypeSafeByFile(File jsonFile, TypeReference<T> t) throws IOException {
        if (jsonFile == null) {
            return (T) null;
        }
        return (T) jsonObjectMapper.readValue(jsonFile, t);
    }

    /**
     * 用jackson的安全方法转换
     * 
     * @param <T>
     * @param json
     * @param t
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static <T> T toObjectSafe(String json, Class<T> t) throws IOException {
        if (json == null || json.length() == 0) {
            return (T) null;
        }
        return (T) jsonObjectMapper.readValue(getSafeJson(json), t);
    }

    /**
     * 将json节点转换成对象
     * 
     * @param node
     * @param t
     * @return
     * @throws IOException
     */
    public static <T> T treeToValue(JsonNode node, Class<T> t) throws IOException {
        return (T) jsonObjectMapper.treeToValue(node, t);
    }

    /**
     * 取安全的Json字符串
     * 
     * @param json
     * @return
     */
    public static String getSafeJson(String json, boolean filterCR) {
        if (json == null || json.length() == 0) {
            return json;
        }
        StringBuilder sb = new StringBuilder(json.length());
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c < 32 || c == 127) {
                if (!filterCR) {
                    if (c != '\r' && c != '\n' && c != '\t') {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            //UTF-8 
            if (c == '\u2028' || c == '\u2029') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String getSafeJson(String json) {
        return getSafeJson(json, true);
    }

    /**
     * 将一个对象转成json字符串 <br/>
     * 字符串对象直接返回该值
     * 
     * @param o
     * @return
     */
    public static String objectToJsonString(Object o) {
        if (o == null) {
            return null;
        }
        //特殊处理
        if (o instanceof String) {
            return o.toString();
        }
        try {
            return jsonObjectMapper.writeValueAsString(o);
        } catch (Exception e) {
            logger.error("objectToJsonString", e);
        }
        return null;
    }

    /**
     * 主要是去掉换行之类的
     * 
     * @param value
     * @return
     */
    public static String printStringValue(String value) {
        if (value != null) {
            String strValue = value;
            strValue = strValue.replaceAll("\r", "\\\\r");
            strValue = strValue.replaceAll("\n", "\\\\n");
            strValue = strValue.replaceAll("\b", "\\\\b");
            strValue = strValue.replaceAll("\f", "\\\\f");
            strValue = strValue.replaceAll("\t", "\\\\t");
            return strValue;
        }
        return value;
    }

    private JsonUtils() {
    }

}
