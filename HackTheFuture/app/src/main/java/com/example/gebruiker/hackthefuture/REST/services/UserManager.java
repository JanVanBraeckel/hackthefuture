package com.example.gebruiker.hackthefuture.REST.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.gebruiker.hackthefuture.REST.framework.Request;

import org.scribe.model.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * UserManager handles OAuth authentication with our REST API.
 */
public class UserManager implements RequestSigner {

    private static UserManager instance;
    private final SharedPreferences prefs;
    private Context context;
    private Token token;

    /**
     * Private constructor for the singleton class {@link be.evavzw.eva21daychallenge.services.UserManager}.
     *
     * @param context the applicationcontext which the {@link be.evavzw.eva21daychallenge.services.UserManager#getInstance(Context)} receives
     */
    private UserManager(Context context) {
        // Fetches the shared preferences and assigns it to a variable
        prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        this.context = context;
    }

    /**
     * This class is a singleton.
     * It will create an instance of itself if one doesn't exist yet.
     * Also checks if our applicationcontext contains an access token and loads it in if it exists.
     *
     * @param context our applicationcontext which we use to determine the Accept-Language for requests and check for presence of existing access tokens.
     * @return returns an instance of itself
     */
    public static UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
            if (instance.checkToken())
                instance.loadToken();
        }

        return instance;
    }

    /**
     * Checks if a token is present
     *
     * @return boolean to indicate whether we already have a token saved for the user
     */
    private boolean checkToken() {
        return prefs.contains("loginAccessToken");
    }

    /**
     * Fetches the access token from the preferences if present and loads it into our class
     */
    private void loadToken() {
        String t = prefs.getString("loginAccessToken", "");
        if (!t.equals(""))
            token = new Token(t, "");
    }

    /**
     * Authorizes a request to the server
     *
     * @param request Request going to the server
     */
    @Override
    public void authorize(Request request) {
        // This shouldn't happen, a user can't log in without a token, but check for it anyway
        if (token == null)
            throw new IllegalArgumentException("Not yet signed in.");

        // Add a bearer token to the Authorization header
        request.addHeader("Authorization", new ArrayList<String>(Arrays.asList(new String[]{"bearer " + token.getToken()})));
    }
}