package com.example.gebruiker.hackthefuture;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.gebruiker.hackthefuture.REST.services.CategoryManager;
import com.example.gebruiker.hackthefuture.models.Category;
import com.example.gebruiker.hackthefuture.models.Item;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Gebruiker on 03/12/2015.
 */
public class CategoryFragment extends Fragment {

    public static Category category;
    private CategoryManager categoryManager;
    private static Resources resources;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list, container, false);
        categoryManager = CategoryManager.getInstance(getContext());
        RecyclerView rv = (RecyclerView) layout.findViewById(R.id.itemList);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey("category")) {
            this.category = (Category) bundle.getSerializable("category");
        }

        resources = getResources();

        new FetchItemsTask(rv).execute();

        return layout;
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Item> items) {
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), items, "Here you can buy items to survive"));
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
            public final ImageView mImageView;
            public final TextView mTextView;

            public Description(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.categoryAvatar);
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
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_description, parent, false);
                return new Description(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                view.setBackgroundResource(mBackground);
                return new ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holderr, final int position) {
            if (holderr instanceof ViewHolder) {
                final ViewHolder holder = (ViewHolder) holderr;
                holder.mItem = mValues.get(position);
                holder.mTextView.setText(mValues.get(position).getName());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetail.class);
                        intent.putExtra("item", holder.mItem);
                        context.startActivity(intent);
                    }
                });

                byte[] decodedString = Base64.decode(category.getIcon().getWhite(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                try{
//                    File file = new File("path");
//                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
//                    decodedByte.compress(Bitmap.CompressFormat.JPEG, 100, os);
//                    os.close();
//
//                    Picasso.with(holder.mImageView.getContext())
//                            .load(file)
//                            .centerCrop()
//                            .into(holder.mImageView);

                }catch(Exception e){
                    throw new IllegalArgumentException(e.getMessage());
                }

            } else if (holderr instanceof Description) {
                byte[] decodedString = Base64.decode(category.getIcon().getBlack(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                final Description holder = (Description) holderr;
                holder.mTextView.setText(description);
                try{
//                    File file = new File("path");
//                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
//                    decodedByte.compress(Bitmap.CompressFormat.JPEG, 100, os);
//                    os.close();
//
//                    Picasso.with(holder.mImageView.getContext())
//                            .load(file)
//                            .centerCrop()
//                            .into(holder.mImageView);

                }catch(Exception e){
                    throw new IllegalArgumentException(e.getMessage());
                }
            }

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    /**
     * An Asynctask that uses the Rest Framework to fetch items
     */
    private class FetchItemsTask extends AsyncTask<String, String, Boolean> {
        RecyclerView recyclerView;
        List<Item> items;

        public FetchItemsTask(RecyclerView recyclerView) {
            super();
            this.recyclerView = recyclerView;
        }

        @Override
        protected Boolean doInBackground(String... objects) {
            try {
                items = categoryManager.getItemsForCategory(category.getId());
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean succeed) {
            if (succeed) {
                setupRecyclerView(recyclerView, items);
            }
        }
    }
}
