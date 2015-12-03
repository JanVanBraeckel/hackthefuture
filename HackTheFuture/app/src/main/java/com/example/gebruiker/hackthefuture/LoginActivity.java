package com.example.gebruiker.hackthefuture;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gebruiker.hackthefuture.REST.services.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        userManager = UserManager.getInstance(getApplicationContext());
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

