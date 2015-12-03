package com.example.gebruiker.hackthefuture.REST.services;

import com.example.gebruiker.hackthefuture.REST.framework.Request;

/**
 * RequestSigner, all of our Managers can implement this, but generally only the UserManager does this
 */
public interface RequestSigner {
    void authorize(Request request);
}
