package com.itacademy.jd2.ai.b2b.dao.jdbc.exception;

public class SQLExecutionException extends RuntimeException {
    private static final long serialVersionUID = 5545045892692168607L;

    public SQLExecutionException(final Exception cause) {
        super(cause);
    }

}
