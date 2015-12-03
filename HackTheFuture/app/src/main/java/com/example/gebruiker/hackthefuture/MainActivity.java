package com.example.gebruiker.hackthefuture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.gebruiker.hackthefuture.REST.services.UserManager;
import com.example.gebruiker.hackthefuture.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private UserManager userManager;

    @Bind(R.id.nrOfCoins)
    TextView numberOfCoins;
    @Bind(R.id.main_content)
    LinearLayout view;
    @Bind(R.id.caps_logo)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = UserManager.getInstance(getApplicationContext());

        ButterKnife.bind(this);

        int caps = getResources().getIdentifier("nuka_cola","drawable", getPackageName());


        Glide.with(this)
                .load(R.drawable.inside_vault2)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().heightPixels) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        BitmapDrawable background = new BitmapDrawable(bitmap);
                        view.setBackgroundDrawable(background);
                    }
                });
Glide.with(this).load(caps).fitCenter().into(img);
        numberOfCoins.setText(String.valueOf(userManager.getUser().getCoins()));
    }

    @OnClick(R.id.btnCategories)
    public void btnCategoriesClick(View v){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

}
