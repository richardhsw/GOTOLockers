package com.example.gotolockers;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LockerTest extends AppCompatActivity {

    private Button requestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_test);

        requestButton = findViewById(R.id.requestBtn);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestLockerInfo task = new RequestLockerInfo();
                String[] params = new String[] {"http://3.14.229.255/test"};
                task.execute(params);
            }
        });
    }
}