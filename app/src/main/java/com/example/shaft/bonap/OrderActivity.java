package com.example.shaft.bonap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.GetCallback;
import com.iainconnor.objectcache.PutCallback;

import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 05/03/2015.
 */
public class OrderActivity extends BaseActivity {
    private List<View> lv = new ArrayList<View>();
    Panier p = new Panier();
    private List<Merchant> merchants = new ArrayList<Merchant>();
    private Recipe recipe;
    private CacheManager mCacheManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setSupportActionBar(getActionBarToolbar());

        getActionBarToolbar().setTitle(R.string.title_activity_command);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Toolbar) findViewById(R.id.actionBarToolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //ON RECUPERE LE NOM MIT DANS LE LOGIN
        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        final String username = prefs.getString("username", null);

        mCacheManager = CacheManager.getInstance(((MyApplication) getApplicationContext()).getDiskCache());
        //RECUPERATION DE LA RECETTE
        recipe = (Recipe) getIntent().getSerializableExtra("item");

        //CREATION DE TOUTS LES MARCHANTS
        createMarchants(merchants);

        LinearLayout parent = ((LinearLayout) findViewById(R.id.content));
        //ICI ON CREER CHAQUE LIGNE DES INGREDIENTS COMPRENANT UN NOM UN EDIT TEXT POUR LA VALEUR ET UN SPINNER POUR LA LISTE DES MARCHANDS QUI VENDENT L INGREDIENT
        for (int i = 0; i < recipe.str_ings.size(); i++) {
            List<String> m = new ArrayList<String>();
            final List<String> dist = new ArrayList<String>();
            final View r = LayoutInflater.from(getBaseContext()).inflate(R.layout.order_elem, parent, false);
            lv.add(r);
            ((TextView) r.findViewById(R.id.ingredient)).setText(recipe.str_ings.get(i));

            //ON RECHERCHE LES MARCHANTS QUI POSSEDENT L INGREDIENT
            searchMerchants(m, dist, i);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, m);

            dataAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);
            ((Spinner) r.findViewById(R.id.spinner)).setAdapter(dataAdapter);
            ((Spinner) r.findViewById(R.id.spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) r.findViewById(R.id.dist)).setText(dist.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ((TextView) r.findViewById(R.id.dist)).setText(dist.get(0));
            parent.addView(r);
        }

        ((Button) findViewById(R.id.order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ON SAVE LE PANIER DANS P
                savePanier(p);
                //ON REGARDE SI LE PANIER DU USER EXISTE DEJA
                Type type = new TypeToken<Panier>() {
                }.getType();
                mCacheManager.getAsync(username, Panier.class, type, new GetCallback() {
                    //SI OUI IL EXISTE
                    @Override
                    public void onSuccess(Object savePanier) {
                        if (savePanier != null) {
                            Panier p2 = (Panier) savePanier;

                            //ON SAVE DANS NOTRE PANIER P TOUS LES INGREDIENTS DE NOTRE ANCIEN PANIER P2
                            if (p2.ingredients.size() > 0)
                                for (int i = 0; i < p2.ingredients.size(); ++i) {
                                    UnitPanier up = new UnitPanier();
                                    p.ingredients.add(p2.ingredients.get(i));
                                    p.ingredients_qt.add(p2.ingredients_qt.get(i));
                                    p.merchants.add(p2.merchants.get(i));
                                }
                        } else {
                            Toast.makeText(OrderActivity.this, "ERREUR SUR PANIER", Toast.LENGTH_SHORT).show();
                        }
                        //ET ON SAVE NOTRE NOUVEAU PANIER P2 DU USER
                        mCacheManager.putAsync(username, p, new PutCallback() {
                            @Override
                            public void onSuccess() {
                                //ET ON LANCE L ACTIVITE PANIER
                                startActivity(new Intent(OrderActivity.this, PanierActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(OrderActivity.this, "ERREUR SUR PANIER", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Panier Vide", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    //ICI ON REGARDE TOUT CE QUE LE USER A REMPLI DANS LES EDIT TEXT DU LAYOUT
    private void savePanier(Panier p) {
        TextView ing;
        String qt;
        Spinner s;

        //ON REGARDE TOUTES LES LIGNES CREER UNE A UNE, ET POUR CHAQUE ELEMENT DONT L EDIT TEXT N EST PAS NUL ON SAVE L INGRDIENT DANS LE PANIER
        for (int i = 0; i < lv.size(); ++i) {
            qt = ((EditText) lv.get(i).findViewById(R.id.qt)).getText().toString();
            if (!qt.equals("")) {
                p.ingredients.add(((TextView) lv.get(i).findViewById(R.id.ingredient)).getText().toString());
                p.ingredients_qt.add(qt);
                p.merchants.add(((Spinner) lv.get(i).findViewById(R.id.spinner)).getSelectedItem().toString());
            }
        }
    }

    private void searchMerchants(List<String> m, List<String> dist, int ite) {
        //ON PARCOURS LA LISTE DES MARCHANTS ET SI LE TYPE D INGREDIENT CORRESPOND ON STOCK LE MARCHANT DANS LA LIST
        for (int i = 0; i < merchants.size(); ++i) {
            for (int j = 0; j < merchants.get(i).ings.size(); ++j)
                if (recipe.ings.get(ite) == merchants.get(i).ings.get(j)) {
                    //STOCK ICI DANS LA LIST M
                    m.add(merchants.get(i).name);
                    dist.add(merchants.get(i).dist);
                }
        }
    }

    private void createMarchants(List<Merchant> merchants) {
        Merchant m0 = new Merchant();
        m0.name = "Marguerite";
        m0.adress = "4 rue Lourmel 75015 Paris";
        m0.type = IngType.BUTCHER;
        m0.ings.add(Ing.JAMBON);
        m0.dist = "1 km";
        Merchant m1 = new Merchant();
        m1.name = "Vent d’Ouest";
        m1.adress = "5 rue Lourmel 75015 Paris";
        m1.type = IngType.FISHMONGER;
        m1.ings.add(Ing.SAINT_JACQUES);
        m1.dist = "2 km";
        Merchant m2 = new Merchant();
        m2.name = "La comtesse du Barry";
        m2.adress = "317 rue de vaugirard 75015 Paris";
        m2.type = IngType.GROCERY;
        m2.ings.add(Ing.FOIE_GRAS);
        m2.ings.add(Ing.BEURRE);
        m2.ings.add(Ing.GROS_ŒUFS);
        m2.ings.add(Ing.CREME_FRAÎCHE);
        m2.ings.add(Ing.SEL_ET_POIVRE_NOIR);
        m2.ings.add(Ing.APREMONT);
        m2.ings.add(Ing.CHOCOLAT);
        m2.ings.add(Ing.LAIT);
        m2.ings.add(Ing.BRIOCHE);
        m2.ings.add(Ing.HUILE_OLIVE);
        m2.ings.add(Ing.POITRINE_FUMEE_EN_TRANCHES);
        m2.dist = "3 km";
        Merchant m3 = new Merchant();
        m3.name = "Mélodie Diététique";
        m3.adress = "2 rue Fondary 75015 Paris";
        m3.type = IngType.GROCERY;
        m3.ings.add(Ing.FOIE_GRAS);
        m3.ings.add(Ing.BEURRE);
        m3.ings.add(Ing.GROS_ŒUFS);
        m3.ings.add(Ing.CREME_FRAÎCHE);
        m3.ings.add(Ing.SEL_ET_POIVRE_NOIR);
        m3.ings.add(Ing.APREMONT);
        m3.ings.add(Ing.CHOCOLAT);
        m3.ings.add(Ing.LAIT);
        m3.ings.add(Ing.BRIOCHE);
        m3.ings.add(Ing.HUILE_OLIVE);
        m3.ings.add(Ing.POITRINE_FUMEE_EN_TRANCHES);
        m3.dist = "4 km";
        Merchant m4 = new Merchant();
        m4.name = "Da Piero";
        m4.adress = "59 avenue Suffren 75007 Paris";
        m4.type = IngType.GROCERY;
        m4.ings.add(Ing.SPAGHETTI);
        m4.ings.add(Ing.PARMESAN_RAPE);
        m4.ings.add(Ing.POITRINE_FUMEE_EN_TRANCHES);
        m4.dist = "5 km";
        Merchant m5 = new Merchant();
        m5.name = "Le marché des saveurs";
        m5.adress = "189 rue croix nivert - 75015 Paris";
        m5.type = IngType.GREENGROCER;
        m5.ings.add(Ing.POMMES_DE_TERRE);
        m5.ings.add(Ing.OIGNONS);
        m5.ings.add(Ing.VANILLE_EN_POUDRE);
        m5.ings.add(Ing.ANANAS);
        m5.ings.add(Ing.BANANES);
        m5.ings.add(Ing.CITRON);
        m5.dist = "6 km";
        Merchant m6 = new Merchant();
        m6.name = "Aubertine";
        m6.adress = "40 rue Fremicourt 75015 Paris";
        m6.type = IngType.CHOCOLATIER;
        m6.dist = "7 km";
        Merchant m7 = new Merchant();
        m7.name = "Poilane";
        m7.adress = "49 boulevard grenelle 75015 Paris";
        m7.type = IngType.BAKERY;
        m7.ings.add(Ing.FARINE_TYPE_55);
        m7.ings.add(Ing.LEVURE);
        m7.dist = "8 km";
        Merchant m8 = new Merchant();
        m8.name = "Cheese";
        m8.adress = "1 rue Desaix 75015 Paris";
        m8.type = IngType.CHEESESHOP;
        m8.ings.add(Ing.REBLOCHON);
        m8.dist = "9 km";
        merchants.add(m0);
        merchants.add(m1);
        merchants.add(m2);
        merchants.add(m3);
        merchants.add(m4);
        merchants.add(m5);
        merchants.add(m6);
        merchants.add(m7);
        merchants.add(m8);
    }
}
