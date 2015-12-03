package com.example.gebruiker.hackthefuture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.gebruiker.hackthefuture.models.Item;

public class ItemDetail extends AppCompatActivity {

    private Item item;

    @Override
    protected void onCreate(Bundle bundleCreate) {
        super.onCreate(bundleCreate);
        setContentView(R.layout.activity_item_detail);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey("item")){
            item = (Item) bundle.getSerializable("item");
        }

        Log.i("item", item.getId());
        Log.i("item", item.getName());
        Log.i("item", String.valueOf(item.getCount()));
        Log.i("item", String.valueOf(item.getValue()));
    }
}
