package it.edelmonte.cocktailapp.fragment;

import static org.koin.java.KoinJavaComponent.inject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.api.CocktailApiClient;
import it.edelmonte.cocktailapp.api.CocktailApiInterface;
import it.edelmonte.cocktailapp.model.CocktailList;
import it.edelmonte.cocktailapp.util.CloudManager;
import kotlin.Lazy;


public class LoadDataFragment extends Fragment{

    private final Lazy<CloudManager> cloudManager = inject(CloudManager.class);
    private ImageView gif;

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
        List<Observable<CocktailList>> requests = new ArrayList<>();
        requests.add(apiService.getCategories());
        requests.add(apiService.getAlcoholic());
        requests.add(apiService.getIngredients());
        requests.add(apiService.getGlasses());

        Observable.zip(requests, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Throwable {
                // Filling filters lists with api responses and saving in singleton
                List<String> categories = ((CocktailList) objects[0]).cocktails.stream().map(e -> e.category).collect(Collectors.toList());
                categories.add(0,"Choose category");
                cloudManager.getValue().setCategories(categories);

                List<String> alcoholics = ((CocktailList) objects[1]).cocktails.stream().map(e -> e.alcoholic).collect(Collectors.toList());
                alcoholics.add(0,"Choose alcholic");
                cloudManager.getValue().setAlcoholic(alcoholics);

                List<String> ingredients = ((CocktailList) objects[2]).cocktails.stream().map(e -> e.ingredient1).collect(Collectors.toList());
                ingredients.add(0,"Choose ingredient");
                cloudManager.getValue().setIngredients(ingredients);

                List<String> glasses = ((CocktailList) objects[3]).cocktails.stream().map(e -> e.glass).collect(Collectors.toList());
                glasses.add(0,"Choose glass");
                cloudManager.getValue().setGlasses(glasses);

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
                //Alert to retry or close the app
                getActivity().runOnUiThread(() ->{
                    new AlertDialog.Builder(getActivity()).setTitle(R.string.attention).setMessage(R.string.loading_error).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loadData();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    }).create().show();
                });
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
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_load_data, container, false);
        gif = frameLayout.findViewById(R.id.gif);
        Glide.with(getActivity()).asGif().load(R.drawable.giphy).into(gif);
        return frameLayout;
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}