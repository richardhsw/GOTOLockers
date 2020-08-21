package com.example.gotolockers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loading extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST = 0;

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideTitleBar();

        setContentView(R.layout.activity_loading);

        checkPermissions();
    }

    /* Hides the title bar in XML so it only displays logo image*/
    private void hideTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }

    /* Start user login activity */
    private void startLogin() {
        Intent intent = new Intent(Loading.this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    /* Checks for denied permissions and request users to allow */
    private void checkPermissions() {
        List<String> requestList = new ArrayList<>();
        for (int index = 0; index < permissions.length; ++index) {
            if (!permissionGranted(permissions[index])) {
                requestList.add(permissions[index]);
            }
        }

        if (!requestList.isEmpty()) {
            requestPermissions(requestList);
        }
        else {
            startLogin();
        }
    }

    /* Check if the user has allowed a particular parmission request */
    private boolean permissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /* Request the user to allow the permission */
    private void requestPermissions(List<String> requestList) {
        String[] requestArr = new String[requestList.size()];
        requestArr = requestList.toArray(requestArr);

        // TODO: Add permission request rationale below
        /*
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
        */

        // No explanation needed; request the permission
        ActivityCompat.requestPermissions(this, requestArr, MY_PERMISSIONS_REQUEST);
        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // TODO: Disable functions for denied permissions.
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                if (grantResults.length == permissions.length) {
                    startLogin();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private boolean allRequestGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
