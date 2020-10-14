package br.com.planejizze.exceptions;

public class PlanejamentoInvalidDate extends RuntimeException {
    public PlanejamentoInvalidDate(String msg) {
        super(msg);
    }

    public PlanejamentoInvalidDate(String msg, Throwable cause) {
        super(msg, cause);
    }
}
