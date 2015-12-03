package com.example.gebruiker.hackthefuture.models;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class Category {
    private String id;
    private String name;
    private Icon icon;

    public Icon getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
