package com.example.shaft.bonap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 05/03/2015.
 */
public class Panier implements Serializable{
    public List<String> ingredients = new ArrayList<String>();
    public List<String> ingredients_qt = new ArrayList<String>();
    public List<String> merchants = new ArrayList<String>();
}
