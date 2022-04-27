package it.edelmonte.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CocktailList {

    @SerializedName("drinks")
    public List<Cocktail> cocktails;

}
