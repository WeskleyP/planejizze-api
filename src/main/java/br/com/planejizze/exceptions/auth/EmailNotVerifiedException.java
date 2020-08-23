package br.com.planejizze.exceptions.auth;

public class EmailNotVerifiedException extends RuntimeException {

    private static final long serialVersionUID = -2587237959032998145L;

    public EmailNotVerifiedException(String msg) {
        super(msg);
    }

    public EmailNotVerifiedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
