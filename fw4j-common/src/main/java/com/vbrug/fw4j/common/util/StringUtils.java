package com.vbrug.fw4j.common.util;

/**
 * 字符串工具类
 *
 * @author vbrug
 * @since 1.0.0
 */
public class StringUtils {


    /**
     * 校验字符串是否有内容，且不可为空字符串
     * @param str 待校验字符串
     * @return Boolean 判断结果
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 校验字符长度
     * @param str 待校验字符
     * @return Boolean 判断结果
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }


    public static String rpad(String src, char fillChar, int length){
        return fillChar(src, fillChar, length, "right");
    }

    public static String lpad(String src, char fillChar, int length){
        return fillChar(src, fillChar, length, "left");
    }

    public static String alignFill(String src, char fillChar, int length){
        if (src.length() >= length ){
            return src;
        }
        boolean lastIsLeft = false;
        while (src.length() < length){
            if (lastIsLeft) {
                src += fillChar;
                lastIsLeft = false;
            } else {
                src = fillChar + src;
                lastIsLeft = true;
            }
        }
        return src;
    }

    private static String fillChar(String src, char fillChar, int length, String fillType){
        if (src.length() >= length ){
            return src;
        }
        while (src.length() < length){
            if ("left".equals(fillType))
                src = fillChar + src;
            else
                src += fillChar;
        }
        return src;
    }
}
