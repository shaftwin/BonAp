package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ReceiptsItemActivity extends BaseActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        // ON RECUPERE LA RECETTE ENVOYER PAR L ACTIVITE PRECEDENTE
        recipe = (Recipe) getIntent().getSerializableExtra("item");

        setSupportActionBar(getActionBarToolbar());
        getActionBarToolbar().setTitle(R.string.title_activity_receipts_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Toolbar) findViewById(R.id.actionBarToolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //SET INFOS A AFFICHER
        ((TextView) findViewById(R.id.recipe_title)).setText(recipe.title);
        ((TextView) findViewById(R.id.recipe_desc)).setText(recipe.description);
        Picasso.with(getApplicationContext())
                .load(recipe.recipePic)
                .centerCrop()
                .fit()
                .into((ImageView) findViewById(R.id.recipe_title_pic));
        //ONCLICK LISTENER QUAND ON CLICK SUR LE BOUTON LIST INGREDIENT
        findViewById(R.id.ingredients).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListIngredientsActivity.class);
                //ON ENVOIE LA RECETTE A LA PROCHAINE ACTI
                intent.putExtra("item", recipe);
                startActivity(intent);
            }
        });
    }

}