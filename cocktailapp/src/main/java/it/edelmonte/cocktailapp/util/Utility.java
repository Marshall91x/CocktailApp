package it.edelmonte.cocktailapp.util;

import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

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
}
