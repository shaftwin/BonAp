package com.example.shaft.bonap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shaft on 05/03/2015.
 */

//LES ADAPTERS SERVENT A GERER CHAQUE ITEM DE LA LISTE AFFICHE DANS LA GRILLE DE CATEGORY
//POUR CHAQUE ELEMENT CREER L ADAPTER SET LES INFOS COMME TITRE ET PICTURE + ACTIVITE A APPELER QUAND ON CLICK SUR L ELEMENT
public class GridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater inflater;

    private List<Category> categories;

    public GridViewAdapter(Context context, List<Category> categories) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Category category = (Category) getItem(position);
        final View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.category_block, parent, false);
            view.setTag(R.id.item_image, view.findViewById(R.id.item_image));
            view.setTag(R.id.grid_item_label, view.findViewById(R.id.grid_item_label));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(mContext, ReceiptsActivity.class);
                        intent.putExtra("type", category.title);
                        mContext.startActivity(intent);
                }
            });
        } else {
            view = convertView;
        }

        //SET ICI DES TITRES ET PICTURE POUR L ITEM
        TextView title = (TextView) view.getTag(R.id.grid_item_label);
        ImageView picture = (ImageView) view.getTag(R.id.item_image);

        Picasso.with(mContext).load(category.categoryPic)
                .placeholder(R.drawable.placeholders)
                .error(R.drawable.placeholders)
                .fit()
                .centerCrop()
                .into(picture);
        title.setText(category.title);

        return view;
    }

}
