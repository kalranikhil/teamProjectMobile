package com.example.nikhil.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edit_email, edit_password;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
        } catch (InflateException i) {
        }

        Log.i(TAG, "In onCreate()");


        button_login = (Button) findViewById(R.id.buttonLogin);
        edit_email = (EditText) findViewById(R.id.EditTextLogin);
        edit_password = (EditText) findViewById(R.id.EditTextPassword);
        SharedPreferences sharedPref = getSharedPreferences("null", Context.MODE_PRIVATE);

        String email = sharedPref.getString("email", "");
            edit_email.setText(email);
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login();
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }

    //Save user info and login
    public void Login() {
        SharedPreferences sharedPref = getSharedPreferences("null", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", edit_email.getText().toString());
        editor.putString("password", edit_password.getText().toString());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
        //SharedPreferences sharedPrefs = getSharedPreferences("null" ,Context.MODE_PRIVATE);

    }
}