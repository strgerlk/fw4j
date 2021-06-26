package com.vbrug.fw4j.common.exception;

import com.vbrug.fw4j.common.util.StringUtils;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class Fw4jException extends RuntimeException {

    public Fw4jException() {
        super();
    }

    public Fw4jException(String message, Throwable cause) {
        super(message, cause);
    }

    public Fw4jException(Throwable cause) {
        super(cause);
    }

    public Fw4jException(String message, Object... args) {
        super(StringUtils.replacePlaceholder(message, "{}", args));
    }

    public Fw4jException(Throwable cause, String message, Object... args) {
        super(StringUtils.replacePlaceholder(message, "{}", args), cause);
    }
}
