package it.edelmonte.cocktailapp.util;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import it.edelmonte.cocktailapp.model.Cocktail;

public class Utility {

    public static SpinnerAdapter createFilterAdapter(FragmentActivity activity) {
        List<String> filters = new ArrayList<>();
        filters.add(" ");
        filters.add("Categories");
        filters.add("Ingredients");
        filters.add("Alcoholic");
        filters.add("Glasses");
        return new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, filters);
    }

    public static ListAdapter createIngredientList(Cocktail cocktail, Activity activity) {
        List<String> ingredients = new ArrayList<>();
        if (cocktail.ingredient1 != null) ingredients.add(cocktail.ingredient1);
        if (cocktail.ingredient2 != null) ingredients.add(cocktail.ingredient2);
        if (cocktail.ingredient3 != null) ingredients.add(cocktail.ingredient3);
        if (cocktail.ingredient4 != null) ingredients.add(cocktail.ingredient4);
        if (cocktail.ingredient5 != null) ingredients.add(cocktail.ingredient5);
        if (cocktail.ingredient6 != null) ingredients.add(cocktail.ingredient6);
        if (cocktail.ingredient7 != null) ingredients.add(cocktail.ingredient7);
        if (cocktail.ingredient8 != null) ingredients.add(cocktail.ingredient8);
        if (cocktail.ingredient9 != null) ingredients.add(cocktail.ingredient9);
        if (cocktail.ingredient10 != null) ingredients.add(cocktail.ingredient10);
        if (cocktail.ingredient11 != null) ingredients.add(cocktail.ingredient11);
        if (cocktail.ingredient12 != null) ingredients.add(cocktail.ingredient12);
        if (cocktail.ingredient13 != null) ingredients.add(cocktail.ingredient13);
        if (cocktail.ingredient14 != null) ingredients.add(cocktail.ingredient14);
        if (cocktail.ingredient15 != null) ingredients.add(cocktail.ingredient15);
        return new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, ingredients);
    }

    public static SpinnerAdapter createLanguagesAdapter(FragmentActivity activity, Cocktail cocktail) {
        List<String> languages = new ArrayList<>();
        if (cocktail.instructionEn != null) languages.add("EN");
        if (cocktail.instructionFr != null) languages.add("FR");
        if (cocktail.instructionDe != null) languages.add("DE");
        if (cocktail.instructionIt != null) languages.add("IT");
        if (cocktail.instructionEs != null) languages.add("ES");
        return new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, languages);
    }
}
