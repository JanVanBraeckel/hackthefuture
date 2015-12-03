package com.example.gebruiker.hackthefuture;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gebruiker.hackthefuture.REST.services.CategoryManager;
import com.example.gebruiker.hackthefuture.REST.services.UserManager;
import com.example.gebruiker.hackthefuture.models.Item;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemDetail extends AppCompatActivity {

    private Item item;
    private CategoryManager categoryManager;
    private UserManager userManager;

    @Bind(R.id.itemName)
    TextView mItemName;

    @Bind(R.id.itemStock)
    TextView mItemStock;

    @Bind(R.id.itemValue)
    TextView mItemValue;


    @Bind(R.id.boy)
    ImageView boy;

    @Bind(R.id.itemDescription)
    TextView mItemDescription;

    @Bind(R.id.itemInInventory)
    TextView mItemInInventory;


    @Override
    protected void onCreate(Bundle bundleCreate) {
        super.onCreate(bundleCreate);
        setContentView(R.layout.activity_item_detail);

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

    private void fillShop(Item item){
        mItemName.setText(item.getName());
        mItemStock.setText(String.valueOf(item.getCount()));
        mItemValue.setText(String.valueOf(item.getValue()));

        setTitle(item.getName());

        int logo_app = getResources().getIdentifier("boy","drawable", getPackageName());


        Glide.with(this)
                .load(logo_app)
                .centerCrop()
                .override(400,400)
                .into(boy);

        mItemDescription.setText(item.getDescription());
        mItemInInventory.setText(String.valueOf(item.getInInventory()));
    }

    @OnClick(R.id.btnBuy)
    public void btnBuyClicked(View v){
        new BuyItemTask().execute(1);
    }

    @OnClick(R.id.btnBuyFive)
    public void btnBuyFiveClicked(View v){
        new BuyItemTask().execute(5);
    }

    private class BuyItemTask extends AsyncTask<Integer, Void, Boolean>{

        private Item item;

        @Override
        protected Boolean doInBackground(Integer... params) {
            try{
                item = categoryManager.buyItem(getItemId(), params[0]);
                userManager.updateUser(item.getValue(), params[0]);
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

    private String getItemId(){
        return item.getId();

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
                fillShop(item);
            }
        }
    }
}
