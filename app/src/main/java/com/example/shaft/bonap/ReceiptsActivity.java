package com.example.shaft.bonap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 03/03/2015.
 */
public class ReceiptsActivity extends BaseActivity implements ReceiptsAdapter.ClickListener {

    private ReceiptsAdapter mReceiptsAdapter;
    private List<Recipe> listReceipts = new ArrayList<>();
    private List<Recipe> adapterData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        //ON RECUPERE LE TYPE DES RECETTE A AFFICHER
        String type = (String) getIntent().getSerializableExtra("type");

        setSupportActionBar(getActionBarToolbar());
        getActionBarToolbar().setTitle(type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Toolbar) findViewById(R.id.actionBarToolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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

        //CREATION DE TOUTES LES RECETTES
        createReceipts(listReceipts);

        //ON PARCOURS LA LISTE DES RECETTES ET ON REGARDE LE TYPE DE LA RECETTE ET S IL EST EGALE A CELUI ENVOYE PAR LIST CATEGORY ON STOCK LA RECETTE
        for (int i = 0; i < listReceipts.size(); ++i) {
            if (listReceipts.get(i).type.equals(type))
                adapterData.add(listReceipts.get(i));
        }
        //ON AFFICHE LES BONNES RECETTES
        mReceiptsAdapter.notifyDataSetChanged();

    }

    //FONCTION APPELER QUAND ON CLICK SUR UNE RECETTE
    @Override
    public void reserchItemClicked(int position) {
        Intent intent = new Intent(this, ReceiptsItemActivity.class);
        //ON ENVOIE LA RECETTE A LA PROCHAINE ACTIVITE
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

    private void createReceipts(List<Recipe> list) {
        Recipe r0 = new Recipe();
        r0.type = "meat";
        r0.title = "Pate Carbonara allégé";
        r0.description = "Amenez une grande casserole d'eau légèrement salée à ébullition. Faites cuire les spaghettis à découvert pendant 12 minutes. Ils doivent rester un peu fermes, al dente.\n" +
                "Pendant ce temps, faites griller la poitrine fumée. Quand elle est bien croustillante, égouttez-la sur du papier absorbant et coupez-la en petits morceaux.\n" +
                "Battez les œufs avec la crème, une pincée de sel et du poivre. Dans une casserole, réchauffez la poitrine dans le beurre à feu doux.\n" +
                "Mettez les spaghettis dans une passoire, rincez-les sous l'eau chaude et égouttez-les très longuement. Versez-les dans la casserole. Dès qu'ils sont réchauffés, incorporez le mélange œufs-crème, mélangez le temps qu'il cuise légèrement et servez aussitôt avec du parmesan.\n" +
                "La poitrine fumée passée au gril est croustillante et peu grasse, mais vous pouvez également la hacher et la frire à feu doux dans le beurre avant de l'ajouter aux spaghettis. Du lait peut remplacer la crème, mais la sauce sera moins onctueuse.\n" +
                "Utilisez de préférence du parmesan fraîchement râpé. Si vous l'achetez en sachet, il a souvent tendance à être sec et beaucoup moins savoureux. Ce fromage, en morceau, se conservera des mois au réfrigérateur, enveloppé dans une feuille de papier sulfurisé puis dans une autre de papier d'aluminium.";
        r0.ingredients = "500 g de spaghetti\n" +
                "Parmesan râpé\n" +
                "250 g de poitrine fumée en tranches\n" +
                "20 g de beurre\n" +
                "3 gros œufs\n" +
                "15 cl de crème fraîche\n" +
                "Sel et poivre noir du moulin";
        r0.str_ings.add("Spaghetti");
        r0.str_ings.add("Parmesan râpé");
        r0.str_ings.add("Poitrine fumée en tranches");
        r0.str_ings.add("Beurre");
        r0.str_ings.add("Gros œufs");
        r0.str_ings.add("Crème Fraîche");
        r0.str_ings.add("Poivre noir du moulin");
        r0.recipePic = R.drawable.patte;
        r0.ings.add(Ing.SPAGHETTI);
        r0.ings.add(Ing.POITRINE_FUMEE_EN_TRANCHES);
        r0.ings.add(Ing.PARMESAN_RAPE);
        r0.ings.add(Ing.BEURRE);
        r0.ings.add(Ing.GROS_ŒUFS);
        r0.ings.add(Ing.CREME_FRAÎCHE);
        r0.ings.add(Ing.SEL_ET_POIVRE_NOIR);
        Recipe r1 = new Recipe();
        r1.type = "meat";
        r1.title = "Tartiflette ";
        r1.description = "Faites cuire les pommes de terre avec leur peau. Épluchez-les et coupez-les en rondelles.\n" +
                "\n" +
                "Émincez les oignons puis faites-les revenir avec un peu de beurre. Une fois dorés, y ajouter le jambon, ainsi que les pommes de terre. Laissez mijoter 15 min.\n" +
                "\n" +
                "Si vous utilisez du vin blanc, c'est le moment de l'ajouter. Salez un peu, poivrez, laissez les pommes de terre s'imprégner du vin blanc quelques minutes avant de transférer le tout dans un plat à gratin.\n" +
                "\n" +
                "Grattez au couteau les reblochons, coupez-les en 2 dans le sens de l'épaisseur et posez les sur les pommes de terre.\n" +
                "\n" +
                "Faites cuire à four chaud (220°C) pendant 20 à 30 min. Servez avec une salade verte, voire quelques tomates, juste assaisonnées d'un peu de vinaigre d'échalote.";
        r1.ingredients = "1 reblochon et demi (2 pour les gourmands\n" +
                "1,2 kg de pommes de terre\n" +
                "1 tranche de jambon fumé coupée en dés (ou bien des lardons ou des dés de bacon)\n" +
                "500 g d'oignons\n" +
                "40 cl d'Apremont ou autre blanc de Savoie sec (facultatif, mais donne plus de goût)\n" +
                "1 pincée de sel\n" +
                "1 pincée de poivre";
        r1.str_ings.add("Reblochon");
        r1.str_ings.add("Jambon fumé");
        r1.str_ings.add("Pomme de terre");
        r1.str_ings.add("Oignons");
        r1.str_ings.add("Apremont");
        r1.str_ings.add("Poivre noir du moulin");
        r1.ings.add(Ing.REBLOCHON);
        r1.ings.add(Ing.JAMBON);
        r1.ings.add(Ing.POMMES_DE_TERRE);
        r1.ings.add(Ing.OIGNONS);
        r1.ings.add(Ing.APREMONT);
        r1.ings.add(Ing.SEL_ET_POIVRE_NOIR);
        r1.recipePic = R.drawable.tartiflette;
        Recipe r2 = new Recipe();
        r2.type = "fish";
        r2.title = "Coquilles saint Jacques au foie gras";
        r2.description = "Rincer les noix de Saint-Jacques. Les sécher avec du papier absorbant. Les couper en deux ou en trois dans le sens de l'épaisseur. \n" +
                "\n" +
                "Couper le foie gras en petits morceaux. Faire fondre le beurre et y faire revenir les noix à feu vif, 30 secondes de chaque côté. Saler les noix. \n" +
                "\n" +
                "Baisser le feu et mettre les dés de foie gras sur les noix de Saint-Jacques, et les laisser fondre. \n" +
                "\n" +
                "Déposer au centre de chaque assiette les Saint-Jacques puis recouvrir de sauce.";
        r2.ingredients = "8 noix de saint Jacques\n" +
                "100 g de foie gras\n" +
                "25 g de beurre";
        r2.str_ings.add("Noix de saint Jacques");
        r2.str_ings.add("Foie gras");
        r2.str_ings.add("Beurre");
        r2.ings.add(Ing.SAINT_JACQUES);
        r2.ings.add(Ing.FOIE_GRAS);
        r2.ings.add(Ing.BEURRE);
        r2.recipePic = R.drawable.saint;
        Recipe r3 = new Recipe();
        r3.type = "vegetarian";
        r3.title = "Pains";
        r3.description = "Mélangez la farine, l'huile d'olive, le sachet de levure, le sel et ajoutez l'eau. Malaxez jusqu'à l'obtention d'une pâte homogène. Le geste est important. Faites comme si vous étiez en train de plier un mouchoir avec la pâte.\n" +
                "Attention la pâte ne doit pas coller à la paroi! Rajoutez de la farine si elle colle, ou de l'eau si elle est trop sèche. Prenez un moule à cake et tapissez de papier cuisson, mettez le pain, faites les croisillons avec un couteau pointu.\n" +
                "Prenez un torchon propre, mouillez-le et mettez-le sur le pain.\n" +
                "Attendez une heure que la pâte soit levée.\n" +
                "Pendant ce temps-là, préchauffez le four à thermostat 7 ou à 220°C pendant 20 mn environ.\n" +
                "Enfournez pendant 40 mn.";
        r3.ingredients = "500 g de farine type 55\n" +
                "1 sachet de levure de boulangerie en granulés à temps de levée réduite\n" +
                "1,5 verre d'eau chaude\n" +
                "1 cuillère à café pleine de sel\n" +
                "1 cuillère à soupe d'huile d'olive";
        r3.str_ings.add("Farine type 55");
        r3.str_ings.add("Levure");
        r3.str_ings.add("Poivre noir du moulin");
        r3.str_ings.add("Huile d'olive");
        r3.ings.add(Ing.FARINE_TYPE_55);
        r3.ings.add(Ing.LEVURE);
        r3.ings.add(Ing.SEL_ET_POIVRE_NOIR);
        r3.ings.add(Ing.HUILE_OLIVE);
        r3.recipePic = R.drawable.pain;
        Recipe r4 = new Recipe();
        r4.type = "dessert";
        r4.title = "Fondue au chocolat maison";
        r4.description = "Mettre le chocolat dans un caquelon avec le lait. \n" +
                "\n" +
                "Chauffer sur feu très doux pour faire fondre le chocolat.\n" +
                "\n" +
                "Hors feu, incorporer le beurre, puis la crème liquide. \n" +
                "\n" +
                "Parfumer de vanille.\n" +
                "\n" +
                "Tremper les morceaux de fruits préalablement citronnés, et les bouts de brioche dans la sauce au chocolat.";
        r4.ingredients = "300g de chocolat noir riche en cacao\n" +
                "10 cl de crème liquide\n" +
                "15 cl de lait\n" +
                "50g de beurre\n" +
                "1/2 cuillère à café de vanille en poudre\n" +
                "250g de brioche\n" +
                "1/2 boîte d'ananas en tranches\n" +
                "3 bananes\n" +
                "le jus d'un 1/2 citron";
        r4.str_ings.add("Chocolat");
        r4.str_ings.add("Crème liquide");
        r4.str_ings.add("Lait");
        r4.str_ings.add("Beurre");
        r4.str_ings.add("Vanille en poudre");
        r4.str_ings.add("Brioche");
        r4.str_ings.add("Ananas");
        r4.str_ings.add("Bananes");
        r4.str_ings.add("Citron");
        r4.ings.add(Ing.CHOCOLAT);
        r4.ings.add(Ing.CREME_FRAÎCHE);
        r4.ings.add(Ing.LAIT);
        r4.ings.add(Ing.BEURRE);
        r4.ings.add(Ing.VANILLE_EN_POUDRE);
        r4.ings.add(Ing.BRIOCHE);
        r4.ings.add(Ing.ANANAS);
        r4.ings.add(Ing.BANANES);
        r4.ings.add(Ing.CITRON);
        r4.recipePic = R.drawable.fondue;
        list.add(r0);
        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
    }

}
