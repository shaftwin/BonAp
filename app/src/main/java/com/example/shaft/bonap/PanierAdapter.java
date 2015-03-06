package com.example.shaft.bonap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shaft on 06/03/2015.
 */
public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.MyViewHolder> {
    private List<UnitPanier> unitPanier;
    private LayoutInflater inflater;
    private ClickListener clickListener;

    public PanierAdapter(Context context, List<UnitPanier> unitPanier) {
        inflater = LayoutInflater.from(context);
        this.unitPanier = unitPanier;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.panier_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(unitPanier.get(i).ingredients);
        myViewHolder.qt.setText(unitPanier.get(i).ingredients_qt);
        myViewHolder.merchant.setText(unitPanier.get(i).merchants);
        myViewHolder.supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitPanier.remove(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return unitPanier.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void reserchItemClicked(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView qt;
        TextView merchant;
        Button supp;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.ing_title);
            qt = (TextView) itemView.findViewById(R.id.ing_qt);
            merchant = ((TextView) itemView.findViewById(R.id.merchant));
            supp = (Button) itemView.findViewById(R.id.supp);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.reserchItemClicked(getPosition());
            }
        }
    }
}