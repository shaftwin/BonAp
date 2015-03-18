package com.example.shaft.bonap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        toolbar.setTitle(R.string.title_activity_login);

        mLogin = (EditText) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);


        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {

            //TODO SET DEFAULT LOGIN

            @Override
            public void onClick(View v) {
                //VERIFICATION LOGIN
                if (mLogin.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Login is needed", Toast.LENGTH_SHORT).show();
                    return ;
                }
                //STOCKAGE DANS LES SHARED PREFERENCES DU LOGIN
                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                editor.putString("username", mLogin.getText().toString());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
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
