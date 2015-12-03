package com.example.gebruiker.hackthefuture.REST.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.gebruiker.hackthefuture.REST.RegisterRestMethod;
import com.example.gebruiker.hackthefuture.REST.SignInRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.models.User;

import org.scribe.model.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * UserManager handles OAuth authentication with our REST API.
 */
public class UserManager implements RequestSigner {

    private User user;
    private static UserManager instance;
    private final SharedPreferences prefs;
    private Context context;
    //private Token token;

    /**
     * Private constructor for the singleton class {@link UserManager}.
     *
     * @param context the applicationcontext which the {@link UserManager#getInstance(Context)} receives
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
//            if (instance.checkToken())
//                instance.loadToken();
        }

        return instance;
    }

    public User getUser() {
        if(user == null)
            return null;
        return user;
    }

    /**
     * Authorizes a request to the server
     *
     * @param request Request going to the server
     */
    @Override
    public void authorize(Request request) {
        // Add a bearer token to the Authorization header
        request.addHeader("Session", new ArrayList<>(Arrays.asList(new String[]{user.getSession()})));
    }

    public void registerUser(String email, String password) {
        new RegisterRestMethod.Builder(context)
                .email(email)
                .password(password)
                .build()
                .execute();
    }

    public void login(String email, String password) {
        SignInRestMethod signin = new SignInRestMethod(context);
        signin.setCredentials(email, password);
        user = signin.execute().getResource();
    }

    public void updateUser(double value, int amount) {
        int coins  = user.getCoins();
        int updatedCoins = (int) (coins - (value*amount));
        user.setCoins(updatedCoins);
    }

    public void updateUserSell(int value, Integer param) {
        int coins = user.getCoins();
        int updatedCoins = (int) (coins + (value * param));
        user.setCoins(updatedCoins);
    }
}