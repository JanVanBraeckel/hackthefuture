package com.example.gebruiker.hackthefuture.REST.framework;

public class RestMethodResult<T> {

    private int statusCode = 0;
    private String statusMsg;
    private T resource;

    public RestMethodResult(int statusCode, String statusMsg, T resource) {
        super();
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.resource = resource;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public T getResource() {
        return resource;
    }

}
