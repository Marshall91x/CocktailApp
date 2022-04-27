package it.edelmonte.cocktailapp.repository;

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
        Call<CocktailList> call = apiService.getCocktails("filter.php?"+s);
        call.enqueue(new Callback<CocktailList>() {
            @Override
            public void onResponse(Call<CocktailList> call, Response<CocktailList> response) {
                if(response.isSuccessful()){
                    cocktails.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CocktailList> call, Throwable t) {
                cocktails.postValue(null);
            }
        });
        return cocktails;
    }
}
