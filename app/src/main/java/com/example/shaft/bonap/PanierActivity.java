package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.GetCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 05/03/2015.
 */
public class PanierActivity extends BaseActivity implements PanierAdapter.ClickListener{
    private static final int PANIER_ID = 2;
    private CacheManager mCacheManager;
    private Panier p;
    private PanierAdapter mPanierAdapter;
    private List<UnitPanier> adapterData = new ArrayList<UnitPanier>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        getActionBarToolbar().setTitle(R.string.title_activity_panier);
        setSupportActionBar(getActionBarToolbar());

        Type type = new TypeToken<Panier>() {
        }.getType();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mPanierAdapter = new PanierAdapter(getApplicationContext(), adapterData);
        mPanierAdapter.setClickListener(PanierActivity.this);

        mRecyclerView.setAdapter(mPanierAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCacheManager = CacheManager.getInstance(((MyApplication) getApplicationContext()).getDiskCache());
        mCacheManager = CacheManager.getInstance(((MyApplication) getApplicationContext()).getDiskCache());
        mCacheManager.getAsync("panier", Panier.class, type, new GetCallback() {
            @Override
            public void onSuccess(Object savePanier) {
                if (savePanier != null) {
                    p = (Panier) savePanier;
                    Toast.makeText(getApplicationContext(), "WIN PANIER", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < p.ingredients.size(); ++i) {
                        UnitPanier up = new UnitPanier();
                        up.ingredients = p.ingredients.get(i);
                        up.ingredients_qt = p.ingredients_qt.get(i);
                        up.merchants = p.merchants.get(i);
                        adapterData.add(up);
                    }
                    mPanierAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getApplicationContext(), "ERREUR PANIER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "ERREUR PANIER", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void reserchItemClicked(int position) {
    }

    protected int getSelfNavDrawerItem() {
        return PANIER_ID;
    }
}
