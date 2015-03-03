package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ReceiptsActivity extends BaseActivity implements ReceiptsAdapter.ClickListener{

    private ReceiptsAdapter mReceiptsAdapter;
    private List<Receipts> adapterData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        getActionBarToolbar().setTitle(R.string.title_activity_receipts);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mReceiptsAdapter = new ReceiptsAdapter(this, adapterData);
        mReceiptsAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mReceiptsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Receipts c1 = new Receipts();
        c1.title = "TOTO";
        c1.description = "TEST";
        adapterData.add(c1);
        mReceiptsAdapter.notifyDataSetChanged();
    }



    @Override
    public void reserchItemClicked(int position) {
        Intent intent = new Intent(this, ReceiptsItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

}
