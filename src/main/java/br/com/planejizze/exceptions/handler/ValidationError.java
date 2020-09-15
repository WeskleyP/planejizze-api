package br.com.planejizze.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private final List<FieldMessage> list = new ArrayList<>();

    public ValidationError(Integer status, String message, Long timeStamp, String error, String path) {
        super(status, message, timeStamp, error, path);
    }

    public List<FieldMessage> getErrors() {
        return this.list;
    }

    public void addError(String fieldName, String mensagem) {
        list.add(new FieldMessage(fieldName, mensagem));
    }

}