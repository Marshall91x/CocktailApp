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
    private MutableLiveData <CocktailList> cocktails;

    public static CocktailRepository getInstance() {
        if(cocktailRepository == null){
            cocktailRepository = new CocktailRepository();
        }
        return cocktailRepository;
    }

    public CocktailRepository(){
        apiService = CocktailApiClient.getClient().create(CocktailApiInterface.class);
    }

}
