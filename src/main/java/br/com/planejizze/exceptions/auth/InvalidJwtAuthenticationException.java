package br.com.planejizze.exceptions.auth;

public class InvalidJwtAuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 5490888314254381373L;

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }

    public InvalidJwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
