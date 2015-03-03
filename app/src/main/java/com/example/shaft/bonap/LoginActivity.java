package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Shaft on 03/03/2015.
 */

public class LoginActivity  extends ActionBarActivity {

    private Toolbar toolbar;
    private EditText mLogin;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.title_activity_login);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mLogin = (EditText) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {

            //TODO SET DEFAULT LOGIN

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.create_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(intent);
            }
        });
    }
}
