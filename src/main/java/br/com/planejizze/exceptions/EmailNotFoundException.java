package br.com.planejizze.exceptions;

public class EmailNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5346345615320834397L;

    public EmailNotFoundException(String msg) {
        super(msg);
    }

    public EmailNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
