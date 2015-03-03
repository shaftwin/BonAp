package com.example.shaft.bonap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shaft on 03/03/2015.
 */
public class CategoriesActivity extends BaseActivity {

    private int Category_ID = 1;

    static final String[] CATEGORIES = new String[]{
            "meat", "fish", "vegetarian", "dessert", "ingredients"};
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getActionBarToolbar().setTitle(R.string.title_activity_receipts);
        setSupportActionBar(getActionBarToolbar());

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setAdapter(new ImageAdapter(this, CATEGORIES));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.grid_item_label))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected int getSelfNavDrawerItem() {
        return Category_ID;
    }
}
