package it.edelmonte.cocktailapp.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.edelmonte.cocktailapp.model.CocktailList;
import it.edelmonte.cocktailapp.repository.CocktailRepository;

public class CocktailViewModel extends ViewModel {

    private MutableLiveData<CocktailList> cocktails = new MutableLiveData<>();
    private CocktailRepository cocktailRepository;

    public CocktailViewModel(){
        cocktailRepository = new CocktailRepository();
    }

    public MutableLiveData<CocktailList> getCocktails(String filter, String value) {
        cocktails = getCocktailsRepo(filter , value);
        return cocktails;
    }

    public MutableLiveData<CocktailList> getCocktailsRepo(String filter, String value) {
        //Calling repository
        cocktails = cocktailRepository.getCocktails(filter + "=" + value);
        return cocktails;
    }

}
