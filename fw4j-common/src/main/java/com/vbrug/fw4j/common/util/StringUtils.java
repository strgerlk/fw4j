package com.vbrug.fw4j.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author vbrug
 * @since 1.0.0
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence sequence){
        return !hasText(sequence);
    }

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

    /**
     * 下划线转驼峰
     *
     * @param str 待处理字符串
     * @return 驼峰字符串
     */
    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     *
     * @param str 待处理字符串
     * @return 下划线字符串
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }



    /**
     * 驼峰转下划线(效率高于{@link #humpToLine(String)})
     *
     * @param str 待处理字符串
     * @return 下划线字符串
     */
    public static String humpToLine2(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 字符串右补位
     *
     * @param src 源字符串
     * @param fillChar 补位字符
     * @param length 总长度
     * @return 补位后的字符串
     */
    public static String rpad(String src, char fillChar, int length){
        return fillChar(src, fillChar, length, "right");
    }

    /**
     * 字符串左补位
     *
     * @param src 源字符串
     * @param fillChar 补位字符
     * @param length 总长度
     * @return 补位后的字符串
     */
    public static String lpad(String src, char fillChar, int length){
        return fillChar(src, fillChar, length, "left");
    }

    /**
     * 字符串补位
     *
     * @param src 源字符串
     * @param fillChar 补位字符
     * @param length 总长度
     * @param fillType 填补类型
     * @return 补位后的字符串
     */
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
