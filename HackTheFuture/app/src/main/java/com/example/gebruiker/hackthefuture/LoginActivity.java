package com.example.gebruiker.hackthefuture;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        }
    }

    @OnClick(R.id.register)
    public void registerClicked(View v){

    }


    private class AuthorizeTask extends AsyncTask<Void, Void, Boolean>{

    }
}

