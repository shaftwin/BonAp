package com.example.shaft.bonap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.GetCallback;
import com.iainconnor.objectcache.PutCallback;

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

        //ON RECUPERE LE USERNAME DU LOGIN
        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        final String username = prefs.getString("username", null);
        mCacheManager = CacheManager.getInstance(((MyApplication) getApplicationContext()).getDiskCache());
        mCacheManager = CacheManager.getInstance(((MyApplication) getApplicationContext()).getDiskCache());
        //ON RECUPERE LE PANIER SI IL EXISTE
        mCacheManager.getAsync(username, Panier.class, type, new GetCallback() {
            //SI IL EXISTE
            @Override
            public void onSuccess(Object savePanier) {
                if (savePanier != null) {
                    p = (Panier) savePanier;

                    //ON PARCOURS TOUT LES INGREDIENTS DU PANIER, ON LES RECUPERES DANS UN UNITPANIER ET ON LE STOCK DANS L ADAPTER
                    for (int i = 0; i < p.ingredients.size(); ++i) {
                        UnitPanier up = new UnitPanier();
                        up.ingredients = p.ingredients.get(i);
                        up.ingredients_qt = p.ingredients_qt.get(i);
                        up.merchants = p.merchants.get(i);
                        adapterData.add(up);
                    }
                    //ON AFFICHE CE QUI A CHANGER DANS L ADAPTER
                    mPanierAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getApplicationContext(), "ERREUR PANIER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "NO PANIER", Toast.LENGTH_SHORT).show();
            }
        });

        //APPELE QUAND ON CLICK SUR LE BOUTON COMMANDER
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LIST D INGREDIENT VIDE ON QUITTE DIRECTE
                if (adapterData.isEmpty()) {
                    Toast.makeText(PanierActivity.this, "Nothing to Send", Toast.LENGTH_SHORT).show();
                    return ;
                }
                //SINON ON CLEAR LA LIST D INGREDIENT DIT ADAPTER
                adapterData.clear();
                //ON VIDE LE PANIER
                final List<String> ls = new ArrayList<String>(p.merchants);
                p.ingredients.clear();
                p.merchants.clear();
                p.ingredients_qt.clear();
                //ON RECUPERE LE USERNAME DU LOGIN
                SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
                final String username = prefs.getString("username", null);
                //ON STOCK POUR LE USERNAME LE NOUVEAU PANIER
                mCacheManager.putAsync(username, p, new PutCallback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(PanierActivity.this, MapActivity.class);
                        //ON ENVOIE LA RECETTE A LA PROCHAINE ACTIVITE
                        intent.putExtra("item", (java.io.Serializable) ls);
                        startActivity(intent);
                        //TODO ADRESS MAIL
                        
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(PanierActivity.this, "ERREUR SUR PANIER", Toast.LENGTH_SHORT).show();
                    }
                });
                //ON AFFICHE LE NOUVEAU PANIER
                mPanierAdapter.notifyDataSetChanged();
            }
        });

    }

    //FONCTION APPELE QUAND ON CLICK SUR UN ITEM
    @Override
    public void suppItemClicked(int position) {
        //ON SUPPRIME L INGREDIENT DE LA LIST ADAPTER
        adapterData.remove(position);
        //ET ON SUPPRIME DU PANIER
        p.ingredients.remove(position);
        p.merchants.remove(position);
        p.ingredients_qt.remove(position);
        //RECUPERATION DU USERNAME DU LOGIN
        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        final String username = prefs.getString("username", null);
        //ET ON STOCK LE NOUVEAU PANIER
        mCacheManager.putAsync(username, p, new PutCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(PanierActivity.this, "ERREUR SUR PANIER", Toast.LENGTH_SHORT).show();
            }
        });
        //ON AFFICHE LE NOUVEAU PANIER
        mPanierAdapter.notifyItemRemoved(position);
    }

    protected int getSelfNavDrawerItem() {
        return PANIER_ID;
    }
}
