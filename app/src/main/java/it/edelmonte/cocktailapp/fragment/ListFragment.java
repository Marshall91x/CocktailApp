package it.edelmonte.cocktailapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;

import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.ViewModel.CocktailViewModel;


public class ListFragment extends Fragment {

    private Spinner filterSpinner, listSpinner;
    private RecyclerView cocktailRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CocktailViewModel model = new ViewModelProvider(this).get(CocktailViewModel.class);
        model.init();
        model.getCocktails().observe(this, cocktailList -> {
            // Recycler flow
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_list, container, false);
        init(frameLayout);
        return frameLayout;
    }

    private void init(FrameLayout frameLayout) {
        filterSpinner = frameLayout.findViewById(R.id.filter_spinner);
        listSpinner = frameLayout.findViewById(R.id.list_spinner);
        cocktailRecycler = frameLayout.findViewById(R.id.cocktail_recycler);
    }
}