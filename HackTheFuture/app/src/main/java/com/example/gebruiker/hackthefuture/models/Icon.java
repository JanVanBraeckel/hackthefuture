package com.example.gebruiker.hackthefuture.models;

import java.io.Serializable;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class Icon implements Serializable{
    private String white, black;

    public String getBlack() {
        return black;
    }

    public String getWhite() {
        return white;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public void setWhite(String white) {
        this.white = white;
    }
}
