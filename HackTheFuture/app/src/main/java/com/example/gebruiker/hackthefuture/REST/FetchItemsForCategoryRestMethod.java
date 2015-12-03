package com.example.gebruiker.hackthefuture.REST;

import android.content.Context;

import com.example.gebruiker.hackthefuture.REST.framework.AbstractRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.REST.framework.RestMethodFactory;
import com.example.gebruiker.hackthefuture.models.Category;
import com.example.gebruiker.hackthefuture.models.Icon;
import com.example.gebruiker.hackthefuture.models.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class FetchItemsForCategoryRestMethod extends AbstractRestMethod<List<Item>>{
    private Context context;
    private String categoryId;

    public FetchItemsForCategoryRestMethod(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Builds the {@link Request}
     *
     * @return returns the built {@link Request}
     */
    @Override
    protected Request buildRequest() {
        try {
            URI REQURL = URI.create("http://cloud.cozmos.be:2400/api/items?category="+categoryId);
            Request r = new Request(RestMethodFactory.Method.GET, REQURL, new byte[]{});
            return r;

        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not build request");
        }

    }


    @Override
    protected List<Item> parseResponseBody(String responseBody) throws Exception {
        JSONArray jsonArray = new JSONArray(responseBody);
        List<Item> items = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject row = jsonArray.getJSONObject(i);
            Item item = new Item();
            item.setName(row.getString("name"));
            item.setId(row.getString("id"));
            item.setValue(row.getInt("value"));
            item.setCount(row.getInt("count"));
            items.add(item);
        }

        return items;
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
        JSONObject obj = new JSONObject(responseBody);
        if(obj.has("message")){
            throw new IllegalArgumentException(obj.getString("message"));
        }
    }

    /**
     * Overwrite the hook {@link AbstractRestMethod#requiresAuthorization()} as logging in obviously doesn't require authorization
     *
     * @return indicator whether to authenticate the request
     */
    @Override
    protected boolean requiresAuthorization() {
        return true;
    }
}
