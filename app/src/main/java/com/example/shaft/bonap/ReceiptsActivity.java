package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ReceiptsActivity extends BaseActivity implements ReceiptsAdapter.ClickListener {

    private ReceiptsAdapter mReceiptsAdapter;
    private List<Receipts> adapterData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        String type = (String) getIntent().getSerializableExtra("type");

        getActionBarToolbar().setTitle(type);
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

        switch (type) {
            case "meat": {
                Receipts c1 = new Receipts();
                c1.title = "Pate Carbonara allégé";
                c1.description = "Amenez une grande casserole d'eau légèrement salée à ébullition. Faites cuire les spaghettis à découvert pendant 12 minutes. Ils doivent rester un peu fermes, al dente.\n" +
                        "Pendant ce temps, faites griller la poitrine fumée. Quand elle est bien croustillante, égouttez-la sur du papier absorbant et coupez-la en petits morceaux.\n" +
                        "Battez les œufs avec la crème, une pincée de sel et du poivre. Dans une casserole, réchauffez la poitrine dans le beurre à feu doux.\n" +
                        "Mettez les spaghettis dans une passoire, rincez-les sous l'eau chaude et égouttez-les très longuement. Versez-les dans la casserole. Dès qu'ils sont réchauffés, incorporez le mélange œufs-crème, mélangez le temps qu'il cuise légèrement et servez aussitôt avec du parmesan.\n" +
                        "La poitrine fumée passée au gril est croustillante et peu grasse, mais vous pouvez également la hacher et la frire à feu doux dans le beurre avant de l'ajouter aux spaghettis. Du lait peut remplacer la crème, mais la sauce sera moins onctueuse.\n" +
                        "Utilisez de préférence du parmesan fraîchement râpé. Si vous l'achetez en sachet, il a souvent tendance à être sec et beaucoup moins savoureux. Ce fromage, en morceau, se conservera des mois au réfrigérateur, enveloppé dans une feuille de papier sulfurisé puis dans une autre de papier d'aluminium.";
                c1.recipePic = R.drawable.patte;
                adapterData.add(c1);
                Receipts c2 = new Receipts();
                c2.title = "Tartiflette ";
                c2.description = "Faites cuire les pommes de terre avec leur peau. Épluchez-les et coupez-les en rondelles.\n" +
                        "\n" +
                        "Émincez les oignons puis faites-les revenir avec un peu de beurre. Une fois dorés, y ajouter le jambon, ainsi que les pommes de terre. Laissez mijoter 15 min.\n" +
                        "\n" +
                        "Si vous utilisez du vin blanc, c'est le moment de l'ajouter. Salez un peu, poivrez, laissez les pommes de terre s'imprégner du vin blanc quelques minutes avant de transférer le tout dans un plat à gratin.\n" +
                        "\n" +
                        "Grattez au couteau les reblochons, coupez-les en 2 dans le sens de l'épaisseur et posez les sur les pommes de terre.\n" +
                        "\n" +
                        "Faites cuire à four chaud (220°C) pendant 20 à 30 min. Servez avec une salade verte, voire quelques tomates, juste assaisonnées d'un peu de vinaigre d'échalote.";
                c2.recipePic = R.drawable.tartiflette;
                adapterData.add(c2);
                mReceiptsAdapter.notifyDataSetChanged();
                break;
            }
            case "fish": {
                Receipts c1 = new Receipts();
                c1.title = "Coquilles saint Jacques au foie gras";
                c1.description = "Rincer les noix de Saint-Jacques. Les sécher avec du papier absorbant. Les couper en deux ou en trois dans le sens de l'épaisseur. \n" +
                        "\n" +
                        "Couper le foie gras en petits morceaux. Faire fondre le beurre et y faire revenir les noix à feu vif, 30 secondes de chaque côté. Saler les noix. \n" +
                        "\n" +
                        "Baisser le feu et mettre les dés de foie gras sur les noix de Saint-Jacques, et les laisser fondre. \n" +
                        "\n" +
                        "Déposer au centre de chaque assiette les Saint-Jacques puis recouvrir de sauce.";
                c1.recipePic = R.drawable.saint;
                adapterData.add(c1);
                mReceiptsAdapter.notifyDataSetChanged();
                break;
            }
            case "vegetarian": {
                Receipts c1 = new Receipts();
                c1.title = "Pains";
                c1.description = "Mélangez la farine, l'huile d'olive, le sachet de levure, le sel et ajoutez l'eau. Malaxez jusqu'à l'obtention d'une pâte homogène. Le geste est important. Faites comme si vous étiez en train de plier un mouchoir avec la pâte.\n" +
                        "Attention la pâte ne doit pas coller à la paroi! Rajoutez de la farine si elle colle, ou de l'eau si elle est trop sèche. Prenez un moule à cake et tapissez de papier cuisson, mettez le pain, faites les croisillons avec un couteau pointu.\n" +
                        "Prenez un torchon propre, mouillez-le et mettez-le sur le pain.\n" +
                        "Attendez une heure que la pâte soit levée.\n" +
                        "Pendant ce temps-là, préchauffez le four à thermostat 7 ou à 220°C pendant 20 mn environ.\n" +
                        "Enfournez pendant 40 mn.";
                c1.recipePic = R.drawable.pain;
                adapterData.add(c1);
                mReceiptsAdapter.notifyDataSetChanged();
                break;
            }
            case "dessert": {
                Receipts c1 = new Receipts();
                c1.title = "Fondue au chocolat maison";
                c1.description = "Mettre le chocolat dans un caquelon avec le lait. \n" +
                        "\n" +
                        "Chauffer sur feu très doux pour faire fondre le chocolat.\n" +
                        "\n" +
                        "Hors feu, incorporer le beurre, puis la crème liquide. \n" +
                        "\n" +
                        "Parfumer de vanille.\n" +
                        "\n" +
                        "Tremper les morceaux de fruits préalablement citronnés, et les bouts de brioche dans la sauce au chocolat.";
                c1.recipePic = R.drawable.fondue;
                adapterData.add(c1);
                mReceiptsAdapter.notifyDataSetChanged();
                break;
            }
            case "ingredients": {
                Receipts c1 = new Receipts();
                c1.title = "Pate Carbonara allégé";
                c1.description = "";
                adapterData.add(c1);
                mReceiptsAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void reserchItemClicked(int position) {
        Intent intent = new Intent(this, ReceiptsItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

}
