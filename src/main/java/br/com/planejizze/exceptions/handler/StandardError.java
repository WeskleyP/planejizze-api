package br.com.planejizze.exceptions.handler;

public class StandardError {

    private static final long serialVersionUID = 1L;
    private Integer status;
    private String message;
    private Long timeStamp;
    private String error;
    private String path;


    public StandardError() {
    }

    public StandardError(Integer status, String message, Long timeStamp, String error, String path) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.error = error;
        this.path = path;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}