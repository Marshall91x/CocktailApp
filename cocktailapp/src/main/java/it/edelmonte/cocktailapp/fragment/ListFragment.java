package it.edelmonte.cocktailapp.fragment;

import static org.koin.java.KoinJavaComponent.inject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.ViewModel.CocktailViewModel;
import it.edelmonte.cocktailapp.adapter.CocktailAdapter;
import it.edelmonte.cocktailapp.model.CocktailList;
import it.edelmonte.cocktailapp.util.CloudManager;
import it.edelmonte.cocktailapp.util.Constants;
import it.edelmonte.cocktailapp.util.Utility;
import kotlin.Lazy;


public class ListFragment extends Fragment {

    private Spinner filterSpinner, listSpinner;
    private RecyclerView cocktailRecycler;
    private CocktailViewModel model;
    private CocktailAdapter adapter;
    private final Lazy<CloudManager> cloudManager = inject(CloudManager.class);
    private String callParameter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CocktailViewModel.class);
        model.getCocktails(null, null).observe(this, cocktailList -> {
            // Recycler flow
            Log.d("My datat lenght: ", cocktailList != null ? String.valueOf(cocktailList.cocktails.size()) : String.valueOf(0));
            if (cocktailList != null) {
                initRecycler(cocktailList);
            }
        });
    }

    private void initRecycler(CocktailList cocktailList) {
        adapter = new CocktailAdapter(cocktailList.getCocktails(), getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        cocktailRecycler.setLayoutManager(layoutManager);
        cocktailRecycler.setAdapter(adapter);
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
        filterSpinner.setAdapter(Utility.createFilterAdapter(getActivity()));
        listSpinner = frameLayout.findViewById(R.id.list_spinner);
        listSpinner.setEnabled(false);
        cocktailRecycler = frameLayout.findViewById(R.id.cocktail_recycler);
        listener();
    }

    private void listener() {
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> listSpinnerAdapter;
                String filter = (String) adapterView.getSelectedItem();
                callParameter = ((String) adapterView.getSelectedItem()).toLowerCase().substring(0, 1);
                switch (filter) {
                    case Constants.INGREDIENTS:
                        listSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cloudManager.getValue().getIngredients());
                        listSpinner.setEnabled(true);
                        break;
                    case Constants.ALCOHOLICS:
                        listSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cloudManager.getValue().getAlcoholic());
                        listSpinner.setEnabled(true);
                        break;
                    case Constants.CATEGORIES:
                        listSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cloudManager.getValue().getCategories());
                        listSpinner.setEnabled(true);
                        break;
                    case Constants.GLASSES:
                        listSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cloudManager.getValue().getGlasses());
                        listSpinner.setEnabled(true);
                        break;
                    default:
                        listSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<>());
                        listSpinner.setEnabled(false);
                        break;
                }
                listSpinner.setAdapter(listSpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model.getCocktails(callParameter, (String) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}