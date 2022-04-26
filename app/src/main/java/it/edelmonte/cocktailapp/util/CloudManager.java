package it.edelmonte.cocktailapp.util;

import java.util.List;

import it.edelmonte.cocktailapp.model.Drink;

public class CloudManager {

    private List<Drink> categories;
    private List<Drink> ingredients;
    private List<Drink> glasses;
    private List<Drink> alcoholic;

    public List<Drink> getCategories() {
        return categories;
    }

    public void setCategories(List<Drink> categories) {
        this.categories = categories;
    }

    public List<Drink> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Drink> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Drink> getGlasses() {
        return glasses;
    }

    public void setGlasses(List<Drink> glasses) {
        this.glasses = glasses;
    }

    public List<Drink> getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(List<Drink> alcoholic) {
        this.alcoholic = alcoholic;
    }
}
