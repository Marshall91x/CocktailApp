package it.edelmonte.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CocktailList {

    @SerializedName("drinks")
    public List<Cocktail> cocktails;

    public List<Cocktail> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }
}
