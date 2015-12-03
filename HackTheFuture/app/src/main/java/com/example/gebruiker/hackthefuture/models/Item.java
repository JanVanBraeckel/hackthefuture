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
    private int inInventory;
    private String description;


    public int getInInventory() {
        return inInventory;
    }

    public String getDescription() {
        return description;
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInInventory(int inInventory) {
        this.inInventory = inInventory;
    }
}
