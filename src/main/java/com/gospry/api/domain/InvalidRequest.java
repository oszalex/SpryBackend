package com.gospry.api.domain;

/**
 * Created by chris on 25/04/15.
 */
public class InvalidRequest {
    private String message;
    private String header;
    private String url;
    private Class<? extends Exception> exceptionClass;

    public InvalidRequest(String message){
        this.message=message;
    }

    public InvalidRequest(String message, String url, Class<? extends Exception> exceptionClass){
        this.message=message;
        this.url=url;
        this.exceptionClass=exceptionClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<? extends Exception> getException() {
        return exceptionClass;
    }

    public void setException(Class<Exception> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }
}
