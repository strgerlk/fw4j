package com.vbrug.fw4j.common.text;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class TextParser {

    private final String text;

    private final List<String> wordList = new ArrayList<>();

    public TextParser(String text) {
        this.text = text;
        this.parseWord();
    }

    public List<String> getWordList(){
        return this.wordList;
    }

    public void parseWord() {
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == '(')
                wordList.add("(");
            else if (aChar == ')')
                wordList.add(")");
            else if (this.isCharNumberAndUnderline(String.valueOf(aChar))) {
                sb.append(aChar);
            } else {
                if (sb.length() > 0)
                    wordList.add(sb.toString());
                sb.setLength(0);
            }
        }
        if (sb.length() > 0)
            wordList.add(sb.toString());
    }


    private Boolean isCharNumberAndUnderline(CharSequence str) {
        Boolean is = false;
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
