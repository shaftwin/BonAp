package com.example.shaft.bonap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;
    LayoutInflater inflater;

    public ImageAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.category_block, null);
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(mobileValues[position]);

            String mobile = mobileValues[position];

            if (mobile.equals("meat")) {
                Picasso.with(context).load(R.drawable.meat)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            } else if (mobile.equals("fish")) {
                Picasso.with(context).load(R.drawable.fish)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            } else if (mobile.equals("vegetarian")) {
                Picasso.with(context).load(R.drawable.vegetarian)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            } else if (mobile.equals("dessert")){
                Picasso.with(context).load(R.drawable.dessert)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            } else {
                Picasso.with(context).load(R.drawable.ingredients)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }

        } else {
            gridView = convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}