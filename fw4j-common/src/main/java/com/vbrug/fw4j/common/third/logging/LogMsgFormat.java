package com.vbrug.fw4j.common.third.logging;


/**
 * format log msg to print
 * @author LK
 * @since 1.0
 */
public abstract class LogMsgFormat {

    /**
     * format msg by stage
     * @param msg the log to output
     * @return return formatting msg
     */
    public static String stage(Object msg){

        String stage = "[STAGE] ";
        String stageMark = rpad(stage, '-', 80);

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append(stageMark);
        sb.append(stage);
        sb.append(String.valueOf(msg));
        sb.append(System.lineSeparator());
        sb.append(stageMark);
        return sb.toString();
    }

    /**
     * format log by emphasis
     * @param msg log to output
     * @return formatting log
     */
    public static String emphasis(Object msg){

        String emphasis = "[EMPHASIS] ";
        String emphasisMark = rpad(emphasis, '*', 80);

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append(emphasisMark);
        sb.append(emphasis);
        sb.append(String.valueOf(msg));
        sb.append(System.lineSeparator());
        sb.append(emphasisMark);
        return sb.toString();
    }

    private static String rpad(String emphasis, char c, int i) {
        return emphasis;
    }


}
