package br.com.planejizze.exceptions.auth;

public class EmailExistsException extends RuntimeException {

    private static final long serialVersionUID = -2587237959032998145L;

    public EmailExistsException(String msg) {
        super(msg);
    }

    public EmailExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
