package com.example.gebruiker.hackthefuture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.gebruiker.hackthefuture.REST.services.UserManager;
import com.example.gebruiker.hackthefuture.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private UserManager userManager;

    @Bind(R.id.nrOfCoins)
    TextView numberOfCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = UserManager.getInstance(getApplicationContext());

        ButterKnife.bind(this);

        numberOfCoins.setText(String.valueOf(userManager.getUser().getCoins()));
    }

    @OnClick(R.id.btnCategories)
    public void btnCategoriesClick(View v){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

}
