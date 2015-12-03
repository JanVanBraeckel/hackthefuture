package com.example.gebruiker.hackthefuture.REST;

import android.content.Context;
import android.util.Base64;

import com.example.gebruiker.hackthefuture.REST.framework.AbstractRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.REST.framework.RestMethodFactory;
import com.example.gebruiker.hackthefuture.models.User;

import org.json.JSONObject;
import org.scribe.model.Token;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class SignInRestMethod extends AbstractRestMethod<User> {
    private static final URI TOKENURI = URI.create("http://cloud.cozmos.be:2400/api/users/login");
    private Context context;
    private String email;
    private String password;

    public SignInRestMethod(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Overwrite the hook {@link AbstractRestMethod#requiresAuthorization()} as logging in obviously doesn't require authorization
     *
     * @return indicator whether to authenticate the request
     */
    @Override
    protected boolean requiresAuthorization() {
        return false;
    }

    @Override
    protected Context getContext() {
        return context;
    }

    public void setCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Builds the {@link Request}
     *
     * @return returns the built {@link Request}
     */
    @Override
    protected Request buildRequest() {
        try {
            String authHeader = Base64.encodeToString((email + ":" + password).getBytes(), Base64.DEFAULT);
            Request r = new Request(RestMethodFactory.Method.POST, TOKENURI, new byte[]{});
            r.addHeader("Content-Type", new ArrayList<>(Arrays.asList("application/json")));
            r.addHeader("Authorization", new ArrayList<>(Arrays.asList("Bearer " + authHeader)));
            return r;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot build request see nested exception.", ex);
        }
    }

    /**
     * Parses the response body
     *
     * @param responseBody JSON string returned by the server
     * @return {@link Token} obtained by server
     * @throws Exception
     */
    @Override
    protected User parseResponseBody(String responseBody) throws Exception {
        User user = new User();
        JSONObject jsonObject = new JSONObject(responseBody);
        if(jsonObject.has("session")){
            user.setSession(jsonObject.getString("session"));
        }
        if(jsonObject.has("myCoins")){
            user.setCoins(jsonObject.getInt("myCoins"));
        }
        return user;
    }

    /**
     * Handles any errors the server might throw for this request
     *
     * @param status       server response status
     * @param responseBody response message
     * @throws Exception
     */
    @Override
    protected void handleHttpStatus(int status, String responseBody) throws Exception {
        JSONObject jsonObject = new JSONObject(responseBody);
        if(jsonObject.has("message")){
            throw new Exception(jsonObject.getString("message"));
        }
        throw new Exception("Something went wrong");
    }
}
