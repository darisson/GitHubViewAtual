package br.com.darisson.gitview.event;

public class RequestFailedEvent {

    private int err_message;
    private String message;
    private boolean defaultError;


    public RequestFailedEvent(String message, boolean defaultError, int err_message) {
        this.err_message = err_message;
        this.message = message;
        this.defaultError = defaultError;
    }

    public int getErr_message() {
        return err_message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDefaultError() {
        return defaultError;
    }



}
