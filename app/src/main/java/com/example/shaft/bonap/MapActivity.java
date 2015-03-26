package com.example.shaft.bonap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 06/03/2015.
 */
public class MapActivity extends BaseActivity implements LocationListener {

    private boolean open = false;
    private static final int MAP_ID = 1;
    private GoogleMap gMap;
    GoogleMapOptions options = new GoogleMapOptions();
    private List<Merchant> merchants = new ArrayList<Merchant>();
    private Location location;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //ON SET ICI LE BOUTON INFORMATION
        final Button infos = ((Button) findViewById(R.id.infos));
        infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open == true)
                    return;
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                //CREATION DE LA FENETRE POP UP
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                       500,
                        1050);
                open = true;

                //SET DU BOUTON DISMISS
                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                        open = false;
                    }
                });
                //ON CREER LES IMAGES LIES AUX MARCHANTS
                ((ImageView) popupView.findViewById(R.id.color1)).setBackgroundColor(Color.rgb(255, 0, 0));
                ((ImageView) popupView.findViewById(R.id.color2)).setBackgroundColor(Color.rgb(0, 255, 255));
                ((ImageView) popupView.findViewById(R.id.color3)).setBackgroundColor(Color.rgb(0, 255, 0));
                ((ImageView) popupView.findViewById(R.id.color4)).setBackgroundColor(Color.rgb(255, 165, 0));
                ((ImageView) popupView.findViewById(R.id.color5)).setBackgroundColor(Color.rgb(255, 255, 0));
                ((ImageView) popupView.findViewById(R.id.color6)).setBackgroundColor(Color.rgb(255, 0, 255));
                //SET POSITION DE LA FENETRE POP UP
                popupWindow.showAsDropDown(infos, 50, -30);

            }
        });


        //ON DESACTIVE LA BOUSSOLE
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false);
        gMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.mapView)).getMap();
        gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            // ON SET ICI LES INFOS A AFFICHER QUAND ON CLICK SUR UN MARCHANT DANS LA MAP
            @Override
            public View getInfoContents(Marker arg0) {
                View v = getLayoutInflater().inflate(R.layout.windowlayout, null);

                String test = arg0.getId();
                int i = 0;
                while (!Character.isDigit(test.charAt(i))) i++;
                int j = i;
                while (j < test.length() && Character.isDigit(test.charAt(j))) j++;
                int ite = Integer.parseInt(test.substring(i, j));
                TextView name = (TextView) v.findViewById(R.id.mer_name);
                name.setText(merchants.get(ite).name);
                TextView adress = (TextView) v.findViewById(R.id.adress);
                adress.setText(merchants.get(ite).adress);

                return v;

            }
        });


        gMap.setMyLocationEnabled(true);
        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        provider = locationManager.getBestProvider(criteria, true);
        // Getting Current Location
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //ICI ON CENTRE LA CAMERA SUR LE USER
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);

            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            locationManager.requestLocationUpdates(provider, 1, 0, this);
        }
        //ON CREER LA LIST DES MARCHANTS
        createMarchants(merchants);
        for (int i = 0; i < merchants.size(); ++i) {
            //ON SET UN MARKER SUR CHAQUE ADRESSES
            setMarkerFromAdd(merchants.get(i).adress, merchants.get(i).type);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
        // Showing the current location in Google Map
        //gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    protected int getSelfNavDrawerItem() {
        return MAP_ID;
    }

    //FONCTION QUI PERMET DE SET UN MARKER SUR LA MAP SELON UNE ADRESSE
    public void setMarkerFromAdd(String strAddress, IngType type) {

        gMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.mapView)).getMap();
        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses;
        try {
            if ((addresses = geocoder.getFromLocationName(strAddress, 1)) != null) {
                if (addresses.size() > 0) {
                    double latitude = addresses.get(0).getLatitude();
                    double longitude = addresses.get(0).getLongitude();

                    //SELON LE TYPE DE MARCHANT ON CREER UN BOUTON D UNE CERTAINE COULEUR
                    if (type == IngType.BUTCHER)
                        gMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    else if (type == IngType.FISHMONGER)
                        gMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    else if (type == IngType.GREENGROCER)
                        gMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    else if (type == IngType.BAKERY)
                        gMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    else if (type == IngType.CHEESESHOP)
                        gMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    else if (type == IngType.GROCERY)
                        gMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMarchants(List<Merchant> merchants) {
        Merchant m0 = new Merchant();
        m0.name = "Marguerite";
        m0.adress = "4 rue Lourmel 75015 Paris";
        m0.type = IngType.BUTCHER;
        m0.ings.add(Ing.JAMBON);
        m0.dist = "2 km";
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
        m2.dist = "2 km";
        Merchant m3 = new Merchant();
        m3.name = "Mélodie Diététique";
        m3.adress = "2 rue Fondary 75015 Paris";
        m3.type = IngType.GROCERY;
        m3.ings.add(Ing.BEURRE);
        m3.ings.add(Ing.GROS_ŒUFS);
        m3.ings.add(Ing.CREME_FRAÎCHE);
        m3.ings.add(Ing.SEL_ET_POIVRE_NOIR);
        m3.ings.add(Ing.APREMONT);
        m3.ings.add(Ing.CHOCOLAT);
        m3.ings.add(Ing.LAIT);
        m3.ings.add(Ing.BRIOCHE);
        m3.ings.add(Ing.HUILE_OLIVE);
        m3.dist = "2 km";
        Merchant m4 = new Merchant();
        m4.name = "Da Piero";
        m4.adress = "59 avenue Suffren 75007 Paris";
        m4.type = IngType.GROCERY;
        m4.ings.add(Ing.SPAGHETTI);
        m4.ings.add(Ing.PARMESAN_RAPE);
        m4.ings.add(Ing.POITRINE_FUMEE_EN_TRANCHES);
        m4.dist = "1.5 km";
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
        m5.dist = "2.4 km";
        Merchant m6 = new Merchant();
        m6.name = "Aubertine";
        m6.adress = "40 rue Fremicourt 75015 Paris";
        m6.type = IngType.CHOCOLATIER;
        m6.dist = "2.5 km";
        Merchant m7 = new Merchant();
        m7.name = "Poilane";
        m7.adress = "49 boulevard grenelle 75015 Paris";
        m7.type = IngType.BAKERY;
        m7.ings.add(Ing.FARINE_TYPE_55);
        m7.ings.add(Ing.LEVURE);
        m7.dist = "1 km";
        Merchant m8 = new Merchant();
        m8.name = "Cheese";
        m8.adress = "1 rue Desaix 75015 Paris";
        m8.type = IngType.CHEESESHOP;
        m8.ings.add(Ing.REBLOCHON);
        m8.dist = "3 km";
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