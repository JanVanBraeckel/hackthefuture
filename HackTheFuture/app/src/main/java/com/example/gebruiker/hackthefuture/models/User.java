package com.example.gebruiker.hackthefuture.models;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class User {
    private String email;
    private String session;
    private int coins;

    public int getCoins() {
        return coins;
    }

    public String getEmail() {
        return email;
    }

    public String getSession() {
        return session;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
