package com.example.gotolockers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText emailEdit;
    EditText idEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Change the title bar message
        setTitle(R.string.register);

        firstNameEdit = findViewById(R.id.firstNameEdit);
        lastNameEdit = findViewById(R.id.lastNameEdit);
        emailEdit = findViewById(R.id.emailEdit);

        idEdit = findViewById(R.id.idEdit);
        idEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    private void attemptRegister() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        firstNameEdit.setError(null);
        lastNameEdit.setError(null);
        emailEdit.setError(null);
        idEdit.setError(null);

        // Store values at the time of the login attempt.
        String first_name = firstNameEdit.getText().toString();
        String last_name = lastNameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String id = idEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid id.
        if (TextUtils.isEmpty(id)) {
            idEdit.setError(getString(R.string.error_field_required));
            focusView = idEdit;
            cancel = true;
        }
        else if (!isIDValid(id)) {
            idEdit.setError(getString(R.string.error_invalid_id));
            focusView = idEdit;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError(getString(R.string.error_field_required));
            focusView = emailEdit;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailEdit.setError(getString(R.string.error_invalid_email));
            focusView = emailEdit;
            cancel = true;
        }

        // Check for a valid last name, if the user entered one.
        if (TextUtils.isEmpty(first_name)) {
            lastNameEdit.setError(getString(R.string.error_field_required));
            focusView = lastNameEdit;
            cancel = true;
        }

        // Check for a valid first name, if the user entered one.
        if (TextUtils.isEmpty(first_name)) {
            firstNameEdit.setError(getString(R.string.error_field_required));
            focusView = firstNameEdit;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            // TODO: register new account information in database

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "Login information");
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isIDValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
