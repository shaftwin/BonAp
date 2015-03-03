package com.example.shaft.bonap;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shaft on 03/03/2015.
 */
public class CategoryItemActivity extends BaseActivity {

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        category = (Category) getIntent().getSerializableExtra("item");

        getActionBarToolbar().setTitle(R.string.title_activity_category_item);
        setSupportActionBar(getActionBarToolbar());

        ((TextView) findViewById(R.id.category_title)).setText(category.title);
        ((TextView) findViewById(R.id.category_desc)).setText(category.description);
        ((ImageView) findViewById(R.id.categorie_title_pic)).setImageResource(R.drawable.df);
    }

}