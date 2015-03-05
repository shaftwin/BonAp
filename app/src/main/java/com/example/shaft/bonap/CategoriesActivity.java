package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 03/03/2015.
 */
public class CategoriesActivity extends BaseActivity {

    private int CATEGORY_ID = 1;

    private GridViewAdapter mEventsGridAdapter;
    private GridView gridView;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getActionBarToolbar().setTitle(R.string.title_activity_receipts);
        setSupportActionBar(getActionBarToolbar());

        Category meat = new Category();
        meat.title = "meat";
        meat.categoryPic = R.drawable.meat;

        Category fish = new Category();
        fish.title = "fish";
        fish.categoryPic = R.drawable.fish;

        Category dessert = new Category();
        dessert.title = "dessert";
        dessert.categoryPic = R.drawable.dessert;

        Category vegetarian = new Category();
        vegetarian.title = "vegetarian";
        vegetarian.categoryPic = R.drawable.vegetarian;

        Category ingredients = new Category();
        ingredients.title = "ingredients";
        ingredients.categoryPic = R.drawable.ingredients;

        categories.add(meat);
        categories.add(fish);
        categories.add(dessert);
        categories.add(vegetarian);
        categories.add(ingredients);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridViewAdapter(this, categories));

    }

    protected int getSelfNavDrawerItem() {
        return CATEGORY_ID;
    }
}
