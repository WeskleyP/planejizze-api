package br.com.planejizze.exceptions;

public class BadParamsException extends RuntimeException {
    private static final long serialVersionUID = 5441588625028745198L;

    public BadParamsException(String msg) {
        super(msg);
    }

    public BadParamsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
