package it.edelmonte.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

public class Drink {

    @SerializedName("strCategory")
    public String category;

    @SerializedName("strIngredient1")
    public String ingredient;

    @SerializedName("strAlcoholic")
    public String alcoholic;

    @SerializedName("strGlass")
    public String glass;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }
}
