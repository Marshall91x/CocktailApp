package it.edelmonte.cocktailapp.fragment;

import static org.koin.java.KoinJavaComponent.inject;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.ViewModel.CocktailViewModel;
import it.edelmonte.cocktailapp.adapter.CocktailAdapter;
import it.edelmonte.cocktailapp.model.CocktailList;
import it.edelmonte.cocktailapp.util.CloudManager;
import it.edelmonte.cocktailapp.util.Constants;
import it.edelmonte.cocktailapp.util.Utility;
import kotlin.Lazy;


public class ListFragment extends Fragment {

    private Spinner filterSpinner;
    private Spinner listSpinner;
    private RecyclerView cocktailRecycler;
    private LinearLayout noData;
    private CocktailViewModel model;
    private ProgressBar progressBar;
    private CocktailAdapter adapter;
    private final Lazy<CloudManager> cloudManager = inject(CloudManager.class);
    private String callParameter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(getActivity()).setTitle(R.string.attention).setMessage(R.string.app_close).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        });
        model = new ViewModelProvider(this).get(CocktailViewModel.class);
        model.getCocktails(null, null).observe(this, cocktailList -> {
            // Updating ui after api call
            progressBar.setVisibility(View.GONE);
            if (cocktailList != null) {
                cocktailRecycler.setVisibility(View.VISIBLE);
                initRecycler(cocktailList);
            } else {
                noData(true);
            }
        });
    }

    private void noData(boolean show) {
        //Hide recycler if list is null and show filter message
        cocktailRecycler.setVisibility(show ? View.GONE : View.VISIBLE);
        noData.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initRecycler(CocktailList cocktailList) {
        //Create adapter and set to recyclerview
        adapter = new CocktailAdapter(cocktailList.cocktails, getActivity(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        cocktailRecycler.setLayoutManager(layoutManager);
        cocktailRecycler.setAdapter(adapter);
        noData(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_list, container, false);
        init(frameLayout);
        setHasOptionsMenu(true);
        return frameLayout;
    }

    private void init(FrameLayout frameLayout) {
        filterSpinner = frameLayout.findViewById(R.id.filter_spinner);
        filterSpinner.setAdapter(Utility.createFilterAdapter(getActivity()));
        listSpinner = frameLayout.findViewById(R.id.list_spinner);
        listSpinner.setEnabled(false);
        cocktailRecycler = frameLayout.findViewById(R.id.cocktail_recycler);
        progressBar = frameLayout.findViewById(R.id.progress);
        noData = frameLayout.findViewById(R.id.no_data);
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
                if(i != 0){
                    cocktailRecycler.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    model.getCocktails(callParameter, (String) adapterView.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //Add search bar to top bar for filtered search
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.search_cocktail, menu);
        SearchManager searchManager = (SearchManager)
                getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.search_cocktail);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Search...");
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.setTextCursorDrawable(R.drawable.white_cursor);
        } else {
            Field f = null;
            try {
                f = TextView.class.getDeclaredField("mCursorDrawableRes");
                f.setAccessible(true);
                f.set(textView, R.drawable.white_cursor);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter != null){
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }
}