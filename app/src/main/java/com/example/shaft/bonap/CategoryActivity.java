package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 03/03/2015.
 */
public class CategoryActivity extends BaseActivity implements CategoryAdapter.ClickListener{

    private CategoryAdapter mCategoryAdapter;
    private List<Category> adapterData = new ArrayList<>();
    private int Category_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getActionBarToolbar().setTitle(R.string.title_activity_category);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mCategoryAdapter = new CategoryAdapter(this, adapterData);
        mCategoryAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Category c1 = new Category();
        c1.title = "TOTO";
        c1.description = "TEST";
        adapterData.add(c1);
        mCategoryAdapter.notifyDataSetChanged();
    }

    protected int getSelfNavDrawerItem() {
        return Category_ID;
    }

    @Override
    public void reserchItemClicked(int position) {
        Intent intent = new Intent(this, CategoryItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

}
