package it.edelmonte.cocktailapp.model;

import com.google.gson.annotations.SerializedName;

public class Cocktail {

    @SerializedName("idDrink")
    public String id;

    @SerializedName("strDrink")
    public String name;

    @SerializedName("strInstruction")
    public String instructionEn;

    @SerializedName("strInstructionES")
    public String instructionEs;

    @SerializedName("strInstructionDE")
    public String instructionDe;

    @SerializedName("strInstructionFR")
    public String instructionFr;

    @SerializedName("strInstructionIT")
    public String instructionIt;

    @SerializedName("strDrinkThumb")
    public String image;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructionEn() {
        return instructionEn;
    }

    public void setInstructionEn(String instructionEn) {
        this.instructionEn = instructionEn;
    }

    public String getInstructionEs() {
        return instructionEs;
    }

    public void setInstructionEs(String instructionEs) {
        this.instructionEs = instructionEs;
    }

    public String getInstructionDe() {
        return instructionDe;
    }

    public void setInstructionDe(String instructionDe) {
        this.instructionDe = instructionDe;
    }

    public String getInstructionFr() {
        return instructionFr;
    }

    public void setInstructionFr(String instructionFr) {
        this.instructionFr = instructionFr;
    }

    public String getInstructionIt() {
        return instructionIt;
    }

    public void setInstructionIt(String instructionIt) {
        this.instructionIt = instructionIt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
