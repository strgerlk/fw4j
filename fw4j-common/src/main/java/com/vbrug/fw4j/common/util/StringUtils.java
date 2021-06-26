package com.vbrug.fw4j.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author vbrug
 * @since 1.0.0
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence sequence) {
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

    /**
     * 下划线转驼峰
     * @param str 待处理字符串
     * @return 驼峰字符串
     */
    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher      matcher = linePattern.matcher(str);
        StringBuffer sb      = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     * @param str 待处理字符串
     * @return 下划线字符串
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }


    /**
     * 驼峰转下划线(效率高于{@link #humpToLine(String)})
     * @param str 待处理字符串
     * @return 下划线字符串
     */
    public static String humpToLine2(String str) {
        Pattern      humpPattern = Pattern.compile("[A-Z]");
        Matcher      matcher     = humpPattern.matcher(str);
        StringBuffer sb          = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 删除字符串内Tab、换行
     */
    public static String removeTNR(String str) {
        return str.replaceAll("[\\t\\n\\r]", " ");
    }

    /**
     * 删除前后字符
     * @param content
     * @param trimStr
     * @return
     */
    public static String trimStr(String content, String trimStr) {
        if (content.startsWith(trimStr))
            content = content.replace(trimStr, "");
        if (content.endsWith(trimStr))
            content = content.substring(0, content.length() - trimStr.length());
        return content;
    }

    /**
     * 字符串右补位
     * @param src      源字符串
     * @param fillChar 补位字符
     * @param length   总长度
     * @return 补位后的字符串
     */
    public static String rpad(String src, char fillChar, int length) {
        return fillChar(src, fillChar, length, "right");
    }

    /**
     * 字符串左补位
     * @param src      源字符串
     * @param fillChar 补位字符
     * @param length   总长度
     * @return 补位后的字符串
     */
    public static String lpad(String src, char fillChar, int length) {
        return fillChar(src, fillChar, length, "left");
    }

    /**
     * 替换占位符，默认占位符为 “{}”
     * @param src  源数据
     * @param args 参数
     * @return 结果
     */
    public static String replacePlaceholder(String src, Object... args) {
        return replacePlaceholder(src, "{}", args);
    }

    /**
     * 替换占位符
     * @param src         源数据
     * @param placeholder 占位符号
     * @param args        参数
     * @return 结果
     */
    public static String replacePlaceholder(String src, String placeholder, Object... args) {
        placeholder = StringUtils.escapeExprSpecialWord(placeholder);
        for (Object arg : args) {
            String value;
            if (arg instanceof String)
                value = String.valueOf(arg);
            else
                value = JacksonUtils.bean2Json(arg);
            src = src.replaceFirst(placeholder, value);
        }
        return src;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     * @param keyword 关键词
     * @return 结果
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!StringUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * 字符串提取
     * @param content       待提取内容
     * @param regexpPattern 匹配表达式
     * @return 匹配的第一个字符串
     */
    public static String extract(String content, String regexpPattern) {
        return Optional.ofNullable(extractAll(content, regexpPattern)).map(x -> x.get(0)).orElse(null);
    }

    /**
     * 字符串提取
     * @param content       待提取内容
     * @param regexpPattern 匹配表达式
     * @return 匹配到的字符串
     */
    public static List<String> extractAll(String content, String regexpPattern) {
        List<String> resultList = new ArrayList<String>();
        Pattern      p          = Pattern.compile(regexpPattern);
        Matcher      m          = p.matcher(content);
        while (m.find()) {
            resultList.add(m.group());
        }
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    /**
     * 居中补充，左右填补相同字符，填充到相同长度
     * @param src      源字符串
     * @param fillChar 待填充字符
     * @param length   长度
     * @return 填补后的字符串
     */
    public static String alignFill(String src, char fillChar, int length) {
        if (src.length() >= length) {
            return src;
        }
        boolean lastIsLeft = false;
        while (src.length() < length) {
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
     * 字符串补位
     * @param src      源字符串
     * @param fillChar 补位字符
     * @param length   总长度
     * @param fillType 填补类型
     * @return 补位后的字符串
     */
    private static String fillChar(String src, char fillChar, int length, String fillType) {
        if (src.length() >= length) {
            return src;
        }
        while (src.length() < length) {
            if ("left".equals(fillType))
                src = fillChar + src;
            else
                src += fillChar;
        }
        return src;
    }
}
