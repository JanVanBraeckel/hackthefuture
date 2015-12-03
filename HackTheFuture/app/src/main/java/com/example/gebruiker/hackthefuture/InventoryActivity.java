package com.example.gebruiker.hackthefuture;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gebruiker.hackthefuture.REST.services.CategoryManager;
import com.example.gebruiker.hackthefuture.REST.services.UserManager;
import com.example.gebruiker.hackthefuture.models.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InventoryActivity extends AppCompatActivity {

    private UserManager userManager;
    private CategoryManager categoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ButterKnife.bind(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.itemsList);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        userManager = UserManager.getInstance(getApplicationContext());
        categoryManager = CategoryManager.getInstance(getApplicationContext());

        new FetchInventoryTask(rv).execute();
    }

    private void fillListview(RecyclerView rv, List<Item> items){
        rv.setAdapter(new SimpleStringRecyclerViewAdapter(this, items, "Your inventory"));
    }

    /**
     * A custom Adapter with two types of ViewHolders
     */
    public static class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Item> mValues;
        private String description;

        /**
         * A different ViewHolder, used to display the category description as the first item in the RecyclerView
         */
        public static class Description extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTextView;

            public Description(View view) {
                super(view);
                mView = view;
                mTextView = (TextView) view.findViewById(R.id.categoryDescription);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        /**
         * The Viewholder used for actual items (items) in the RecyclerView
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public Item mItem;

            public final View mView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        public Item getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Item> items, String description) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;

            //Add the first item (recipe) twice because the Description ViewHolder overrides the first
            mValues.add(0, mValues.get(0));

            this.description = description;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdescription, parent, false);
                return new Description(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
                view.setBackgroundResource(mBackground);
                return new ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holderr, final int position) {
            if (holderr instanceof ViewHolder) {
                final ViewHolder holder = (ViewHolder) holderr;
                holder.mItem = mValues.get(position);
                holder.mTextView.setText(mValues.get(position).getName() + " - " + mValues.get(position).getCount() + " pieces");

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemSell.class);
                        intent.putExtra("item", holder.mItem);
                        context.startActivity(intent);
                    }
                });
            } else if (holderr instanceof Description) {
                final Description holder = (Description) holderr;
                holder.mTextView.setText(description);
            }

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    private class FetchInventoryTask extends AsyncTask<Void, Void, Boolean>{
        private RecyclerView recyclerView;
        private List<Item> items = new ArrayList<>();

        public FetchInventoryTask(RecyclerView rv) {
            super();
            recyclerView = rv;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                items = categoryManager.fetchInventory();
                return true;
            }
            catch(final Exception e){
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
                if(items.size() != 0)
                fillListview(recyclerView, items);
            }
        }
    }
}
