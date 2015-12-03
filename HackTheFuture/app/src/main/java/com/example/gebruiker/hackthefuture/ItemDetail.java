package com.example.gebruiker.hackthefuture;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gebruiker.hackthefuture.REST.services.CategoryManager;
import com.example.gebruiker.hackthefuture.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetail extends AppCompatActivity {

    private Item item;
    private CategoryManager categoryManager;

    @Bind(R.id.itemName)
    TextView mItemName;

    @Bind(R.id.itemStock)
    TextView mItemStock;

    @Bind(R.id.itemValue)
    TextView mItemValue;

    @Override
    protected void onCreate(Bundle bundleCreate) {
        super.onCreate(bundleCreate);
        setContentView(R.layout.activity_item_detail);

        ButterKnife.bind(this);

        categoryManager = CategoryManager.getInstance(getApplicationContext());

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey("item")){
            item = (Item) bundle.getSerializable("item");
        }

        new FetchItemDetailsTask(item).execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItemName.setText(item.getName());
        mItemStock.setText(String.valueOf(item.getCount()));
        mItemValue.setText(String.valueOf(item.getValue()));
    }

    private class FetchItemDetailsTask extends AsyncTask<Void, Void, Boolean>{

        private Item item;

        public FetchItemDetailsTask(Item item) {
            this.item = item;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                item = categoryManager.getItemDetails(item.getId());
                return true;
            }catch(final Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                Log.i("success", item.getDescription());
            }
        }
    }
}
