package com.example.gebruiker.hackthefuture.REST;

import android.content.Context;
import android.util.Base64;

import com.example.gebruiker.hackthefuture.REST.framework.AbstractRestMethod;
import com.example.gebruiker.hackthefuture.REST.framework.Request;
import com.example.gebruiker.hackthefuture.REST.framework.RestMethodFactory;
import com.example.gebruiker.hackthefuture.models.User;

import org.json.JSONObject;
import org.scribe.model.Token;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class BuyItemRestMethod extends AbstractRestMethod<Void>{
    private Context context;
    private String itemId;
    private Integer amount;

    public BuyItemRestMethod(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected Void parseResponseBody(String responseBody) throws Exception {
        return null;
    }

    @Override
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
            URI REQURI = URI.create("http://cloud.cozmos.be:2400/api/buy");
            JSONObject body = new JSONObject();
            body.put("item", itemId);
            body.put("count", amount);

            Request r = new Request(RestMethodFactory.Method.PUT, REQURI, body.toString().getBytes());
            r.addHeader("Content-Type", new ArrayList<>(Arrays.asList("application/json")));
            return r;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot build request see nested exception.", ex);
        }
    }

    /**
     * Handles any errors the server might throw for this request
     *
     * @param status       server response status
     * @param responseBody response message
     * @throws Exception
     */
    @Override
    protected void handleHttpStatus(int status, String responseBody) throws Exception {
        JSONObject jsonObject = new JSONObject(responseBody);
        if(jsonObject.has("message")){
            throw new Exception(jsonObject.getString("message"));
        }
        throw new Exception("Something went wrong");
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
