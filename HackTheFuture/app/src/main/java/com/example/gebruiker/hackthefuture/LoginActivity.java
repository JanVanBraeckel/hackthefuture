package com.example.gebruiker.hackthefuture;

<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
=======
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
>>>>>>> 6af1e322f799a44f96f4c6ca62dd4a96f52d1080
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
<<<<<<< HEAD
=======
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
>>>>>>> 6af1e322f799a44f96f4c6ca62dd4a96f52d1080
import android.view.View;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.LinearLayout;
=======
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> 6af1e322f799a44f96f4c6ca62dd4a96f52d1080

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
            if(password.length() < 6)
                mPasswordView.setError(getString(R.string.longerThan6));
            else{
                new AuthorizeTask().execute(email, password);
            }
        }
    }

    @OnClick(R.id.register)
    public void registerClicked(View v){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(!email.equals("") && !password.equals("")){
            if(password.length() < 6)
                mPasswordView.setError(getString(R.string.longerThan6));
            else{
                new RegisterTask().execute(email, password);
            }
        }
    }


    private class RegisterTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                userManager.registerUser(params[0], params[1]);
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
                Log.i("register", "registered");
            }
        }
    }

    private class AuthorizeTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                userManager.login(params[0], params[1]);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                LoginActivity.this.finish();
                startActivity(intent);
            }
        }
    }
}

