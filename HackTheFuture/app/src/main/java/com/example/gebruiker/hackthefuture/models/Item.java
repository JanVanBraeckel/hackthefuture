package com.example.gebruiker.hackthefuture.models;

import java.io.Serializable;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class Item implements Serializable{
    private String id;
    private String name;
    private int value;
    private int count;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
