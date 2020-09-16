package br.com.planejizze.exceptions;

public class FileStorageException extends RuntimeException {
    private static final long serialVersionUID = 5490888314254381373L;

    public FileStorageException(String msg) {
        super(msg);
    }

    public FileStorageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
