package br.com.planejizze.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5490888314254381373L;

    public NotFoundException(String msg){
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause){
        super(msg,cause);
    }
}
