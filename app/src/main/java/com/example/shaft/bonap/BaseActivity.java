package com.example.shaft.bonap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

import com.example.shaft.bonap.Drawer.DrawerAdapter;
import com.example.shaft.bonap.Drawer.DrawerRawInfo;
import com.iainconnor.objectcache.CacheManager;

import java.util.ArrayList;
import java.util.List;

//LIST DE TOUT LES INGREDIENTS
enum Ing {
    SPAGHETTI,
    PARMESAN_RAPE,
    POITRINE_FUMEE_EN_TRANCHES,
    JAMBON,
    BEURRE,
    GROS_ŒUFS,
    CREME_FRAÎCHE,
    SEL_ET_POIVRE_NOIR,
    REBLOCHON,
    POMMES_DE_TERRE,
    OIGNONS,
    APREMONT,
    CHOCOLAT,
    CRÈME_LIQUIDE,
    LAIT,
    VANILLE_EN_POUDRE,
    BRIOCHE,
    ANANAS,
    BANANES,
    CITRON,
    SAINT_JACQUES,
    FOIE_GRAS,
    FARINE_TYPE_55,
    LEVURE,
    HUILE_OLIVE
}

//LIST DES TYPE DE MARCHANTS
enum IngType {
    BUTCHER,
    FISHMONGER,
    GREENGROCER,
    CHOCOLATIER,
    BAKERY,
    CHEESESHOP,
    GROCERY
}

//CLASS HERITE PAR TOUTES LES ACTIVITEES
//ELLE SET LE DRAWER, LA TOOLBAR
//DRAWER ==> ELLE EMPECHE DE RELANCER LA MEME ACTIVITE + ELLE SET LES IMAGES DU DRAWER + SET DES ACTIVITEES A LANCER GRACE AU DRAWER


public class BaseActivity extends ActionBarActivity implements DrawerAdapter.ClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar toolbar;
    private ImageView profileImage;
    private Handler mHandler;
    private static final int CATEGORY_ID = 0;
    private static final int MAP_ID = 1;
    private static final int PANIER_ID = 2;
    private static final int PROFIL_ID = 3;
    private static final int SETTINGS_ID = 4;
    private RecyclerViewScrollListener recyclerScrollListener;
    private CacheManager mCacheManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCacheManager = CacheManager.getInstance(((MyApplication) getApplicationContext()).getDiskCache());
        mHandler = new Handler();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupDrawer();
    }

    //SET LE DRAWER ICI
    protected void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, 0, 0);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileImage.setImageResource(R.drawable.profile);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.drawer_recycler);

        DrawerAdapter mDrawerAdapter = new DrawerAdapter(this,
                getDrawerData(this));
        mDrawerAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mDrawerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //SET IMAGES SUR LE DRAWER
    private static List<DrawerRawInfo> getDrawerData(Context context) {
        List<DrawerRawInfo> data = new ArrayList<>();

        int[] icons = {
                R.drawable.ic_list,
                R.drawable.ic_map,
                R.drawable.ic_panier,
                R.drawable.ic_profil,
                R.drawable.ic_settings_grey};
        String[] titles = context.getResources().getStringArray(R.array.drawer_strings);
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            DrawerRawInfo current = new DrawerRawInfo();
            current.title = titles[i];
            current.iconId = icons[i];
            data.add(current);
        }
        return data;
    }

    //FONCTION APPELE QUAND ON CLICK SUR UN LIEN DANS LE DRAWER
    @Override
    public void itemClicked(final int position) {
        if (position == getSelfNavDrawerItem()) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }
        if (isSpecialItem(position)) {
            goToNavDrawerItem(position, 0);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToNavDrawerItem(position, 0);
                }
            }, 250);
            setSelectedNavDrawerItem(position);
        }
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    private void setSelectedNavDrawerItem(int position) {
    }

    protected int getSelfNavDrawerItem() {
        return 0;
    }

    //FONTION APPELE QUAND ON CLICK SUR UNE IMAGE DANS LE DRAWER + LANCE UNE ACTIVITEES EN REGARDANT L ID DU LIEN CLICKE
    private boolean isSpecialItem(int position) {
        return position == SETTINGS_ID;
    }

    protected void goToNavDrawerItem(int position, final int tabId) {
        switch (position) {
            case CATEGORY_ID:
                startActivity(new Intent(this,CategoriesActivity.class));
                finish();
                break;
            case MAP_ID:
                startActivity(new Intent(this, MapActivity.class));
               // finish();
                break;
            case PROFIL_ID:
                startActivity(new Intent(this,ProfilActivity.class));
                //finish();
                break;
            case PANIER_ID:
                startActivity(new Intent(this,PanierActivity.class));
               // finish();
                break;
            case SETTINGS_ID:
                startActivity(new Intent(this, SettingsActivity.class));
                //finish();
                break;
        }
    }

    public Toolbar getActionBarToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
        }
        return toolbar;
    }

    public RecyclerViewScrollListener getRecyclerScrollListener() {
        if (recyclerScrollListener == null)
            recyclerScrollListener = new RecyclerViewScrollListener(getSupportActionBar());
        return recyclerScrollListener;
    }
}
