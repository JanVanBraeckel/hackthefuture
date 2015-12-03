package com.example.gebruiker.hackthefuture;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.gebruiker.hackthefuture.REST.services.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.logoApp)
    ImageView imageView;
    @Bind(R.id.loginScreen)
    LinearLayout login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


        userManager = UserManager.getInstance(getApplicationContext());

        int logo_app = getResources().getIdentifier("logo_app","drawable", getPackageName());


        Glide.with(this)
                .load(logo_app)
                .centerCrop()
                .override(350,350)
                .into(imageView);

        Glide.with(getApplicationContext())
                .load(R.drawable.vault1)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().heightPixels) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        BitmapDrawable background = new BitmapDrawable(bitmap);
                        login.setBackgroundDrawable(background);
                    }
                });
    }

    @OnClick(R.id.sign_in)
    public void sign_inClicked(View v){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(!email.equals("") && !password.equals("")){

        }
    }

    @OnClick(R.id.register)
    public void registerClicked(View v){

    }


//    private class AuthorizeTask extends AsyncTask<Void, Void, Boolean>{
//
//    }
}

