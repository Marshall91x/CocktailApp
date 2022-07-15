package it.edelmonte.cocktailapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import it.edelmonte.cocktailapp.api.CocktailApiClient;
import it.edelmonte.cocktailapp.api.CocktailApiInterface;
import it.edelmonte.cocktailapp.model.CocktailList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CocktailRepository {

    private static CocktailRepository cocktailRepository;
    private CocktailApiInterface apiService;
    private final MutableLiveData <CocktailList> cocktails = new MutableLiveData<>();

    public static CocktailRepository getInstance() {
        if(cocktailRepository == null){
            cocktailRepository = new CocktailRepository();
        }
        return cocktailRepository;
    }

    public CocktailRepository(){
        apiService = CocktailApiClient.getClient().create(CocktailApiInterface.class);
    }

    public MutableLiveData<CocktailList> getCocktails(String s) {
        //Calling api with "dynamic" endpoint creation to use only one api method
        Call<CocktailList> call = apiService.getCocktails("filter.php?"+s);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CocktailList> call, @NonNull Response<CocktailList> response) {
                if (response.isSuccessful()) {
                    cocktails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CocktailList> call, @NonNull Throwable t) {
                cocktails.postValue(null);
            }
        });
        return cocktails;
    }
}
