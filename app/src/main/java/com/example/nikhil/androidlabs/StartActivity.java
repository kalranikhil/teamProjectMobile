package com.example.nikhil.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "Start Activity";
    protected static Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        button=(Button) findViewById(R.id.listButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(intent,10);
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

    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if(requestCode == 10) {
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
        }

        if(responseCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(getApplicationContext(),"ListItemsActivity passed: My information to share",Toast.LENGTH_SHORT);
        }
    }

}
