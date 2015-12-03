package com.example.gebruiker.hackthefuture.REST;

import android.content.Context;
import android.util.Base64;

import com.example.gebruiker.hackthefuture.R;
import com.example.gebruiker.hackthefuture.REST.framework.AbstractRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.REST.framework.RestMethodFactory;

import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class RegisterRestMethod extends AbstractRestMethod<Void>{
    private static final URI REQURL = URI.create("http://cloud.cozmos.be:2400/api/users/register");
    private Context context;
    private String email, password;

    private RegisterRestMethod(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    /**
     * Builds the {@link Request}
     *
     * @return returns the built {@link Request}
     */
    @Override
    protected Request buildRequest() {
        try {
            JSONObject json = new JSONObject();

            json.put("email", email);
            json.put("password", Base64.encodeToString(password.getBytes(), Base64.DEFAULT));

            Request r = new Request(RestMethodFactory.Method.POST, REQURL, json.toString().getBytes());
            r.addHeader("Content-Type", Arrays.asList("application/json"));
            return r;

        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not build request");
        }

    }

    /**
     * There's no return for this request
     *
     * @param responseBody JSON string returned by the server
     * @return <code>Void</code>
     * @throws Exception
     */
    @Override
    protected Void parseResponseBody(String responseBody) throws Exception {
        return null;
    }

    /**
     * Handles any HTTP errors this request may generate
     *
     * @param status       server response status
     * @param responseBody response message
     * @throws Exception
     */
    @Override
    protected void handleHttpStatus(int status, String responseBody) throws Exception {

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

    /**
     * Internal Builder class to make building this request a bit easier
     */
    public static class Builder {
        private Context context;
        private String email;
        private String password;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRestMethod build() {
            RegisterRestMethod rrm = new RegisterRestMethod(context);
            rrm.email = email;
            rrm.password = password;
            return rrm;
        }
    }
}

