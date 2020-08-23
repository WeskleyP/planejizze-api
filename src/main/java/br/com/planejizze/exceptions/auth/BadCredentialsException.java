package br.com.planejizze.exceptions.auth;

public class BadCredentialsException extends RuntimeException{

    private static final long serialVersionUID = -5699780616275845461L;

    public BadCredentialsException(String msg) {
        super(msg);
    }

    public BadCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
