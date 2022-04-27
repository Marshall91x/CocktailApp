package it.edelmonte.cocktailapp.util;

import java.util.List;

import it.edelmonte.cocktailapp.model.Cocktail;

public class CloudManager {

    private List<String> categories;
    private List<String> ingredients;
    private List<String> glasses;
    private List<String> alcoholic;
    private Cocktail cocktail;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getGlasses() {
        return glasses;
    }

    public void setGlasses(List<String> glasses) {
        this.glasses = glasses;
    }

    public List<String> getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(List<String> alcoholic) {
        this.alcoholic = alcoholic;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }
}
