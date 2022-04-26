package it.edelmonte.cocktailapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.edelmonte.cocktailapp.model.CocktailList;
import it.edelmonte.cocktailapp.repository.CocktailRepository;

public class CocktailViewModel extends ViewModel {

    private MutableLiveData <CocktailList> cocktails;
    private CocktailRepository cocktailRepository;

    public void init(){
        if(cocktails == null) {
            cocktailRepository = CocktailRepository.getInstance();
        }
    }

    public LiveData<CocktailList> getCocktails(){
        return cocktails;
    }

}
