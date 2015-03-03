package com.example.shaft.bonap;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ReceiptsItemActivity extends BaseActivity {

    private Receipts receipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        receipts = (Receipts) getIntent().getSerializableExtra("item");

        getActionBarToolbar().setTitle(R.string.title_activity_receipts_item);
        setSupportActionBar(getActionBarToolbar());

        ((TextView) findViewById(R.id.category_title)).setText(receipts.title);
        ((TextView) findViewById(R.id.category_desc)).setText(receipts.description);
        ((ImageView) findViewById(R.id.categorie_title_pic)).setImageResource(R.drawable.df);
    }

}