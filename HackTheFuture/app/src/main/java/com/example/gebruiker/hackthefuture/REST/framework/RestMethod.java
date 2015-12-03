package com.example.gebruiker.hackthefuture.REST.framework;


public interface RestMethod<T> {

    public RestMethodResult<T> execute();
}
