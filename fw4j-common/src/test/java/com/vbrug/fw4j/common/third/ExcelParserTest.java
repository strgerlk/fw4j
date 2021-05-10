package com.vbrug.fw4j.common.third;

import com.vbrug.fw4j.common.text.TextParser;
import com.vbrug.fw4j.common.third.excel.ExcelParserXLSX;
import com.vbrug.fw4j.common.util.FileUtil;
import com.vbrug.fw4j.common.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ExcelParserTest {

    private static final String SEARCH        = "arrayExists(x -> x=0, multiSearchAllPositions(case_desc, splitByString('&', '${}'))) = 0";
    private static final String MATCH         = "match(concat(case_desc, deal_desc), '${}')";
    private static final String EXCLUDE_MATCH = "match(concat(case_desc, deal_desc), '${}') = 0";


    public static void main(String[] args) throws Exception {
        List<Map<String, String>> maps = new ExcelParserXLSX().parseExcel(new FileInputStream(new File("/home/vbrug/downloads/噪音跑接警反馈.xlsx")),
                0, 1, 65000, "theme", "classifyCode", "classifyName", "positiveExpression", "negativeExpression");
        List<String> sqlList = new ArrayList<>();
        sqlList.add("truncate table z_ah_jqfx_dc.d_classify_import;");
        maps.forEach(x -> {
            String positiveExpression = x.get("positiveExpression");
            String negativeExpression = x.get("negativeExpression");
            if (!StringUtils.isEmpty(positiveExpression)) {
                sqlList.add("insert into z_ah_jqfx_dc.d_classify_import(case_no, classify_code) select case_no, '" + x.get("classifyCode") + "' from z_ah_jqfx_dc.d_base_case_h where " + parseExpression(positiveExpression) + ";");
            }
        });
//        sqlList.add("insert into z_ah_jqfx_dc.d_classify_import(case_no, classify_code) select case_no, classify_code from y_ah_jqfx.bs_aitype_case_h;");
//        sqlList.add("truncate table z_ah_jqfx_dc.d_classify_clear;");
//        sqlList.add("insert into z_ah_jqfx_dc.d_classify_clear(case_no, classify_code) select t.case_no, t.classify_code from z_ah_jqfx_dc.d_classify_import t left join y_jqfx_dc_mysql.common_classify_priority a  on t.classify_code = a.classify_code order by a.priority, a.classify_code limit 1 by case_no;");
//
//        sqlList.add("truncate table z_ah_jqfx_dc.d_result_classify;");
//        sqlList.add("insert into z_ah_jqfx_dc.d_result_classify(case_no, classify_code;");
//        sqlList.add("select case_no, new_classify_code from y_ah_jqfx_mysql.bs_case_revert order by revert_time desc limit 1 by case_no;");
//        sqlList.add("insert into z_ah_jqfx_dc.d_result_classify(case_no, classify_code;");
//        sqlList.add("select case_no, classify_code from bs_aitype_case_h where case_no not in (select case_no from z_ah_jqfx_dc.d_result_classify);");
//        sqlList.add("truncate table y_ah_jqfx.bs_aitype_case_h;");
//        sqlList.add("insert  into y_ah_jqfx.bs_aitype_case_h(id, case_no, jcase_no, case_desc, call_time, call_phone, happen_time, happen_address, longitude, latitude, alarm_person, xqdw_code, suboffice, call_summary_code, classify_code, deal_no, deal_desc, deal_summary_code, deal_xqdw_code) select id, t.case_no, jcase_no, case_desc, call_time, call_phone, happen_time, happen_address, longitude, latitude, alarm_person, xqdw_code, suboffice, call_summary_code, if(notEmpty(a.classify_code), a.classify_code, '999999'), deal_no, deal_desc, deal_summary_code, deal_xqdw_code from z_ah_jqfx_dc.d_base_case_h t left join (select * from z_ah_jqfx_dc.d_result_classify limit 1 by case_no )a on t.case_no = a.case_no;");
////        sqlList.add("alter table y_ah_jqfx.bs_aitype_case_h update classify_code='100049' where call_summary_code in( '10014','10015','10016','10017','210400','60110','60111','60112') and classify_code='999999';");
//

        FileUtil.produceFile(new File("/home/vbrug/Develop/Docker/clickhouse/oil_batch.sql"), sqlList);
    }

    private static String parseExpression(String expression) {
        expression = expression.replaceAll("( |\n|\r)", "")
                .replaceAll("）", ")")
                .replaceAll("（", "(")
                .replaceAll("\\|\\)", ")")
                .replaceAll("&\\^", "\\^");
        List<String> wordList   = new TextParser(expression, "&", "^").getWordList();
        boolean      braceStart = false;
        StringBuffer sb         = new StringBuffer();
        String       matchWay   = MATCH;
        for (String word : wordList) {
            if (word.equals("&")) {
                matchWay = MATCH;
                sb.append(" and ");
            } else if (word.equals("^")) {
                matchWay = EXCLUDE_MATCH;
                sb.append(" and ");
            } else {
                while (word.startsWith("(")) {
                    sb.append("(");
                    word = word.substring(1);
                }
                String tmpEnd = "";
                while (word.endsWith(")")) {
                    tmpEnd += ")";
                    word = word.substring(0, word.length() - 1);
                }
                sb.append(matchWay.replace("${}", word)).append(tmpEnd);
            }
        }
        return sb.toString();
    }
}
