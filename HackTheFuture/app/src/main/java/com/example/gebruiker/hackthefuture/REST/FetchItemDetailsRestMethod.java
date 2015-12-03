package com.example.gebruiker.hackthefuture.REST;

import android.content.Context;

import com.example.gebruiker.hackthefuture.REST.framework.AbstractRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.REST.framework.RestMethodFactory;
import com.example.gebruiker.hackthefuture.models.Item;

import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class FetchItemDetailsRestMethod extends AbstractRestMethod<Item> {
    private Context context;
    private String itemId;

    public FetchItemDetailsRestMethod(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Builds the {@link Request}
     *
     * @return returns the built {@link Request}
     */
    @Override
    protected Request buildRequest() {
        try {
            URI REQURL = URI.create("http://cloud.cozmos.be:2400/api/items/" + itemId);
            Request r = new Request(RestMethodFactory.Method.GET, REQURL, new byte[]{});
            return r;

        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not build request");
        }

    }


    @Override
    protected Item parseResponseBody(String responseBody) throws Exception {
        JSONObject jsonObject = new JSONObject(responseBody);
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName(jsonObject.getString("name"));
        item.setId(jsonObject.getString("id"));
        item.setValue(jsonObject.getInt("value"));
        item.setCount(jsonObject.getInt("inStock"));
        item.setInInventory(jsonObject.getInt("inInventory"));
        item.setDescription(jsonObject.getString("description"));
        items.add(item);

        return item;
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
        if (obj.has("message")) {
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
