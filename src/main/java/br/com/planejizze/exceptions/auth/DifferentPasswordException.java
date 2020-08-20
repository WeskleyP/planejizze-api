package br.com.planejizze.exceptions.auth;

public class DifferentPasswordException extends RuntimeException {

    private static final long serialVersionUID = 7238531584029924899L;

    public DifferentPasswordException(String msg) {
        super(msg);
    }

    public DifferentPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
