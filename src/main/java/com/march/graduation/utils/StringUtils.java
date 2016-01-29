
package com.march.graduation.utils;

import org.apache.commons.lang.CharUtils;

public class StringUtils {

    /**
     * 判断字符串是否为纯数字或符号
     * 
     * @param input
     * @return
     */
    public static boolean isAllNumOrSymbol(String input) {
        if (null == input) {
            return true;
        }
        for (char c : input.toCharArray()) {
            if (!CharUtils.isAscii(c)) { //为汉字，肯定不全是数字和字符
                return false;
            }

            //单个字符
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) { //为字母，说明不全是数字和字符
                return false;
            }
        }
        return true;
    }

    /**
     * 获取长度（中文长度为2）
     * 
     * @param input
     * @return
     */
    public static int byteLength(String input) {
        if (null == input) {
            return 0;
        }
        input = input.trim();
        int count = 0;
        for (char c : input.toCharArray()) {
            if (CharUtils.isAscii(c)) {
                count++;
            } else {
                count += 2;
            }
        }
        return count;
    }

    /**
     * 截取字符串
     * 
     * @param input
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String byteSubString(String input, int beginIndex, int endIndex) {
        if (input == null) {
            input = "";
        }
        input = input.trim();
        if (beginIndex >= input.length()) {
            return "";
        }
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex < 0 || endIndex > input.getBytes().length) {
            endIndex = input.getBytes().length;
        }
        if (endIndex < beginIndex) {
            return "";
        }
        int count = beginIndex;
        int realEndIndex = beginIndex;
        for (char c : input.toCharArray()) {
            if (CharUtils.isAscii(c)) {
                count++;
            } else {
                count += 2;
            }
            if (count > endIndex) {
                break;
            }
            realEndIndex++;
        }
        return input.substring(beginIndex, realEndIndex);
    }

    /**
     * 截取字符串
     * 
     * @param input
     * @param length
     * @param ellipsis 省略
     * @return
     */
    public static String subString(String input, int length, String ellipsis) {
        if (org.apache.commons.lang.StringUtils.isBlank(input)) {
            return input;
        }
        ellipsis = ellipsis == null ? "" : ellipsis;
        if (StringUtils.byteLength(input) > length && (length - ellipsis.length()) > 0) {
            return StringUtils.byteSubString(input, 0, length - ellipsis.length()) + ellipsis;
        } else {
            return input;
        }
    }

    private static final char[] CHINESE_NUM = new char[] { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十' };

    private static final char[] CHINESE_SCALE = new char[] { '十', '百', '千', '万' };

    public static String Num2Chinese(int num) {
        if (num < 0 || num > 99){
            return "";
        }
        if(num < 11){
            return "" + CHINESE_NUM[num];
        } else {
            int shi = num / 10;
            String shiStr = null;
            if(shi == 1){
                shiStr = "" + CHINESE_SCALE[0];
            } else {
                shiStr = "" + CHINESE_NUM[shi] + CHINESE_SCALE[0];
            }
            int ge = num % 10;
            
            if(ge == 0){
                return shiStr;
            } else {
                return shiStr + CHINESE_NUM[ge];
            }
        }
    }
}
