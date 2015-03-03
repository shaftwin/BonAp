package com.example.shaft.bonap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shaft on 03/03/2015.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> events;
    private LayoutInflater inflater;
    private ClickListener clickListener;

    public CategoryAdapter(Context context, List<Category> events) {
        inflater = LayoutInflater.from(context);
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.category_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(events.get(i).title);
        //myViewHolder.date.setText(events.get(i).date);
        myViewHolder.description.setText(events.get(i).description);
        myViewHolder.title_pic.setImageResource(R.drawable.df);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void reserchItemClicked(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        //TextView date;
        TextView description;
        ImageView title_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.event_title);
            description = (TextView) itemView.findViewById(R.id.location);
            title_pic = ((ImageView) itemView.findViewById(R.id.title_pic));
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.reserchItemClicked(getPosition());
            }
        }
    }
}