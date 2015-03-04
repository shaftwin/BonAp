package com.example.shaft.bonap;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Shaft on 04/03/2015.
 */
public class ProfilActivity extends BaseActivity {
    private static final int PROFIL_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        ((TextView) findViewById(R.id.user_completename)).setText("Jean Moulin");
        Picasso.with(getApplicationContext())
                .load(R.drawable.profile)
                .centerCrop()
                .fit()
                .into((ImageView) findViewById(R.id.profile_img));
        ((TextView) findViewById(R.id.user_email)).setText("jean.moulin@hotmail.com");
    }

    protected int getSelfNavDrawerItem() {
        return PROFIL_ID;
    }
}
