package com.example.gebruiker.hackthefuture.REST.framework;

import java.util.List;
import java.util.Map;

/**
 * Class which represents a server response
 */
public class Response {

    /**
     * The HTTP status code
     */
    public int status;

    /**
     * The HTTP headers received in the response
     */
    public Map<String, List<String>> headers;

    /**
     * The response body, if any
     */
    public byte[] body;

    protected Response(int status, Map<String, List<String>> headers, byte[] body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }
}

