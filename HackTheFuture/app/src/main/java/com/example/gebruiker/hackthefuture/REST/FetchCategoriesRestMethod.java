package com.example.gebruiker.hackthefuture.REST;

import android.content.Context;
import android.util.Base64;

import com.example.gebruiker.hackthefuture.REST.framework.AbstractRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.REST.framework.RestMethodFactory;
import com.example.gebruiker.hackthefuture.models.Category;
import com.example.gebruiker.hackthefuture.models.Icon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class FetchCategoriesRestMethod extends AbstractRestMethod<List<Category>>{
    private static final URI REQURL = URI.create("http://cloud.cozmos.be:2400/api/categories");
    private Context context;
    private String email, password;

    public FetchCategoriesRestMethod(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    /**
     * Builds the {@link Request}
     *
     * @return returns the built {@link Request}
     */
    @Override
    protected Request buildRequest() {
        try {
            Request r = new Request(RestMethodFactory.Method.GET, REQURL, new byte[]{});
            return r;

        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not build request");
        }

    }

    /**
     * There's no return for this request
     *
     * @param responseBody JSON string returned by the server
     * @return <code>Void</code>
     * @throws Exception
     */
    @Override
    protected List<Category> parseResponseBody(String responseBody) throws Exception {
        JSONArray jsonArray = new JSONArray(responseBody);
        List<Category> categories = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject row = jsonArray.getJSONObject(i);
            Category category = new Category();
            Icon icon = new Icon();
            category.setId(row.getString("id"));
            category.setName(row.getString("name"));
            icon.setBlack(row.getJSONObject("icon").getString("black"));
            icon.setWhite(row.getJSONObject("icon").getString("white"));
            category.setIcon(icon);
            categories.add(category);
        }

        return categories;
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
