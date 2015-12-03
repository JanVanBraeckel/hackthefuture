package com.example.gebruiker.hackthefuture;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gebruiker.hackthefuture.REST.services.CategoryManager;
import com.example.gebruiker.hackthefuture.REST.services.UserManager;
import com.example.gebruiker.hackthefuture.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemSell extends AppCompatActivity {

    private Item item;
    private CategoryManager categoryManager;
    private UserManager userManager;

    @Bind(R.id.itemName)
    TextView mItemName;

    @Bind(R.id.itemValue)
    TextView mItemValue;

    @Bind(R.id.itemDescription)
    TextView mItemDescription;

    @Bind(R.id.itemInInventory)
    TextView mItemInInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_sell);

        ButterKnife.bind(this);

        categoryManager = CategoryManager.getInstance(getApplicationContext());
        userManager = UserManager.getInstance(getApplicationContext());

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey("item")){
            item = (Item) bundle.getSerializable("item");
        }

        new FetchItemDetailsTask(item).execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnSell)
    public void btnSellClicked(View v){
        new SellItemTask().execute(1);
    }

    private void fillShop(Item item){
        mItemName.setText(item.getName());
        mItemValue.setText(String.valueOf(item.getValue()));
        mItemDescription.setText(item.getDescription());
        mItemInInventory.setText(String.valueOf(item.getInInventory()));
    }

    private String getItemId(){
        return item.getId();
    }

    private class SellItemTask extends AsyncTask<Integer, Void, Boolean>{

        private Item item;

        @Override
        protected Boolean doInBackground(Integer... params) {
            try{
                item = categoryManager.sellItem(getItemId(), params[0]);
                userManager.updateUserSell(item.getValue(), params[0]);
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
                fillShop(item);
            }
        }
    }

    private class FetchItemDetailsTask extends AsyncTask<Void, Void, Boolean> {

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
                fillShop(item);
            }
        }
    }
}
