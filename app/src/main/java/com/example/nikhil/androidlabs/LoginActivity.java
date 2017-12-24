package com.example.nikhil.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "Login Activity";
    protected static SharedPreferences prefs;
    protected static  EditText login, password;
    protected static Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.loginField);
        password = (EditText) findViewById((R.id.passwordField));
        loginButton = (Button) findViewById(R.id.loginButton);

         prefs = getSharedPreferences("My Ids", Context.MODE_PRIVATE);
        String email = prefs.getString("email",null);
        String pass = prefs.getString("password",null);
        login.setText(email);
        password.setText(pass);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // SharedPreferences preferences = getSharedPreferences(null, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();

                edit.putString("email",login.getText().toString());
                edit.putString("password",password.getText().toString());
                edit.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });


        Log.i(ACTIVITY_NAME,"in onCreate()");
    }

    public void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"in onResume()");
    }

    public void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"in onStart()");
    }

    public void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"in onPause()");
    }

    public void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME,"in onStop()");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"in onDestroy()");
    }

}
