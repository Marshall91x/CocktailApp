package it.edelmonte.cocktailapp.util;

import java.util.List;

import it.edelmonte.cocktailapp.model.Cocktail;

public class CloudManager {

    private List<Cocktail> categories;
    private List<Cocktail> ingredients;
    private List<Cocktail> glasses;
    private List<Cocktail> alcoholic;

    public List<Cocktail> getCategories() {
        return categories;
    }

    public void setCategories(List<Cocktail> categories) {
        this.categories = categories;
    }

    public List<Cocktail> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Cocktail> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Cocktail> getGlasses() {
        return glasses;
    }

    public void setGlasses(List<Cocktail> glasses) {
        this.glasses = glasses;
    }

    public List<Cocktail> getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(List<Cocktail> alcoholic) {
        this.alcoholic = alcoholic;
    }
}
