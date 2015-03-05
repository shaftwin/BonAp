package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ReceiptsItemActivity extends BaseActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        recipe = (Recipe) getIntent().getSerializableExtra("item");

        getActionBarToolbar().setTitle(R.string.title_activity_receipts_item);
        setSupportActionBar(getActionBarToolbar());

        ((TextView) findViewById(R.id.recipe_title)).setText(recipe.title);
        ((TextView) findViewById(R.id.recipe_desc)).setText(recipe.description);
        ((ImageView) findViewById(R.id.recipe_title_pic)).setImageResource(recipe.recipePic);
        findViewById(R.id.ingredients).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListIngredientsActivity.class);
                intent.putExtra("item", recipe);
                startActivity(intent);
            }
        });
    }

}