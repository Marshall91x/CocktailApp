package it.edelmonte.cocktailapp.fragment;

import static org.koin.java.KoinJavaComponent.inject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.api.CocktailApiInterface;
import it.edelmonte.cocktailapp.api.CocktailApiClient;
import it.edelmonte.cocktailapp.model.Drink;
import it.edelmonte.cocktailapp.model.DrinkList;
import it.edelmonte.cocktailapp.util.CloudManager;
import kotlin.Lazy;


public class LoadDataFragment extends Fragment{

    private final Lazy<CloudManager> cloudManager = inject(CloudManager.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    //Method to concurrent call cocktail apis
    private void loadData() {
        //Create api client
        CocktailApiInterface apiService = CocktailApiClient.getClient().create(CocktailApiInterface.class);

        //Create a requests list for concurrency calls
        List<Observable<DrinkList>> requests = new ArrayList<>();
        requests.add(apiService.getCategories());
        requests.add(apiService.getAlcoholic());
        requests.add(apiService.getIngredients());
        requests.add(apiService.getGlasses());

        Observable.zip(requests, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Throwable {
                // Filling filters lists with api responses and saving in singleton
                cloudManager.getValue().setCategories(((DrinkList) objects[0]).getDrinks());
                cloudManager.getValue().setAlcoholic(((DrinkList) objects[1]).getDrinks());
                cloudManager.getValue().setIngredients(((DrinkList) objects[2]).getDrinks());
                cloudManager.getValue().setGlasses(((DrinkList) objects[3]).getDrinks());
                return objects;
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Oops qualcosa è andato storto...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                //Navigating to list fragment
                Navigation.findNavController(getView()).navigate(LoadDataFragmentDirections.dataToList());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_data, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}