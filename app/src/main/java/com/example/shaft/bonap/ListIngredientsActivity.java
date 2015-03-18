package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Shaft on 05/03/2015.
 */
public class ListIngredientsActivity extends BaseActivity {
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ingredients);

        recipe = (Recipe) getIntent().getSerializableExtra("item");

        setSupportActionBar(getActionBarToolbar());
        getActionBarToolbar().setTitle(R.string.title_activity_list_ingredients);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Toolbar) findViewById(R.id.actionBarToolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ((TextView) findViewById(R.id.list)).setText(recipe.ingredients);

        ((Button) findViewById(R.id.cmd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("item", recipe);
                startActivity(intent);
            }
        });
    }
}
