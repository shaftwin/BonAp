package com.example.shaft.bonap;

import android.os.Bundle;
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

        getActionBarToolbar().setTitle(R.string.title_activity_list_ingredients);
        setSupportActionBar(getActionBarToolbar());

        ((TextView) findViewById(R.id.list)).setText(recipe.ingredients);
    }
}
