package br.com.planejizze.exceptions;

public class ComprovanteException extends RuntimeException {

    private static final long serialVersionUID = -219001871172093969L;

    public ComprovanteException(String msg) {
        super(msg);
    }

    public ComprovanteException(String msg, Throwable cause) {
        super(msg, cause);
    }
}