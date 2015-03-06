package com.example.shaft.bonap;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shaft on 03/03/2015.
 */
public class Recipe implements Serializable{
    public String type;
    public String title;
    public String description;
    public String ingredients;
    public int recipePic;
    public List<Ing> ings = new ArrayList<Ing>();
    public List<String> str_ings = new ArrayList<String>();
}
