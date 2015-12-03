package com.example.gebruiker.hackthefuture.REST.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.gebruiker.hackthefuture.REST.BuyItemRestMethod;
import com.example.gebruiker.hackthefuture.REST.FetchCategoriesRestMethod;
import com.example.gebruiker.hackthefuture.REST.FetchInventoryRestMethod;
import com.example.gebruiker.hackthefuture.REST.FetchItemDetailsRestMethod;
import com.example.gebruiker.hackthefuture.REST.FetchItemsForCategoryRestMethod;
import com.example.gebruiker.hackthefuture.REST.RegisterRestMethod;
import com.example.gebruiker.hackthefuture.REST.SellItemRestMethod;
import com.example.gebruiker.hackthefuture.REST.SignInRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.models.Category;
import com.example.gebruiker.hackthefuture.models.Item;
import com.example.gebruiker.hackthefuture.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class CategoryManager {
    private static CategoryManager instance;
    private final SharedPreferences prefs;
    private Context context;

    /**
     * Private constructor for the singleton class {@link UserManager}.
     *
     * @param context the applicationcontext which the {@link UserManager#getInstance(Context)} receives
     */
    private CategoryManager(Context context) {
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
    public static CategoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new CategoryManager(context);
//            if (instance.checkToken())
//                instance.loadToken();
        }

        return instance;
    }


    public List<Category> getCategories() {
        return new FetchCategoriesRestMethod(context).execute().getResource();
    }

    public List<Item> getItemsForCategory(String categoryId) {
        FetchItemsForCategoryRestMethod method = new FetchItemsForCategoryRestMethod(context);
        method.setCategoryId(categoryId);
        return method.execute().getResource();
    }

    public Item getItemDetails(String itemId) {
        FetchItemDetailsRestMethod method = new FetchItemDetailsRestMethod(context);
        method.setItemId(itemId);
        return method.execute().getResource();
    }

    public Item buyItem(String itemId, Integer amount) {
        BuyItemRestMethod method = new BuyItemRestMethod(context);
        method.setItemId(itemId);
        method.setAmount(amount);
        method.execute();
        return getItemDetails(itemId);
    }

    public List<Item> fetchInventory() {
        return new FetchInventoryRestMethod(context).execute().getResource();

    }

    public Item sellItem(String itemId, Integer param) {
        SellItemRestMethod method = new SellItemRestMethod(context);
        method.setItemId(itemId);
        method.setAmount(param);
        method.execute();
        return getItemDetails(itemId);
    }
}
