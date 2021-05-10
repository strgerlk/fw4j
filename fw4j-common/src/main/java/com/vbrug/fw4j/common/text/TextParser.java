package com.vbrug.fw4j.common.text;

import com.vbrug.fw4j.common.util.ObjectUtils;
import com.vbrug.fw4j.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本解析
 * @author vbrug
 * @since 1.0.0
 */
public class TextParser {

    private final String text;

    private final List<String> wordList = new ArrayList<>();

    private String[] splitSymbols = new String[]{"(", ")", ","};

    public TextParser(String text) {
        this(text, null);
    }

    public TextParser(String text, String... splitSymbols) {
        this.text = text;
        if (ObjectUtils.notNull(splitSymbols))
            this.splitSymbols = splitSymbols;
        this.parseWord();
    }

    public List<String> getWordList() {
        return this.wordList;
    }

    private String matchSymbol(String parseText) {
        for (String symbol : splitSymbols) {
            if (parseText.startsWith(symbol))
                return symbol;
        }
        return null;
    }

    public void parseWord() {
        char[]        chars = text.toCharArray();
        StringBuilder sb    = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            String matchSymbol = this.matchSymbol(text.substring(i));
            if (!StringUtils.isEmpty(matchSymbol)) {
                if (sb.length() > 0) {
                    wordList.add(sb.toString());
                    sb.setLength(0);
                }
                wordList.add(matchSymbol);
                i += matchSymbol.length() - 1;
            } else {
                sb.append(chars[i]);
            }
        }
        if (sb.length() > 0)
            wordList.add(sb.toString());
    }

    private Boolean isCharNumberAndUnderline(CharSequence str) {
        boolean is = false;
        Pattern pt = Pattern.compile("^[0-9a-zA-Z_]+$");
        Matcher mt = pt.matcher(str);
        if (mt.matches()) {
            is = true;
        }
        return is;
    }

    public static void main(String[] args) {
        TextParser textParser = new TextParser("replaceRegexpAll(case_desc, '\\s', '') case_desc, longitude, latitude");
        System.out.println(textParser.text);
    }

}
