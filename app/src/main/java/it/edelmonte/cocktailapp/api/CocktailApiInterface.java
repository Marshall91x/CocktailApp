package it.edelmonte.cocktailapp.api;

import io.reactivex.rxjava3.core.Observable;
import it.edelmonte.cocktailapp.model.DrinkList;
import retrofit2.http.GET;

public interface CocktailApiInterface {

    @GET("list.php?c=list")
    Observable<DrinkList> getCategories();

    @GET("list.php?g=list")
    Observable<DrinkList> getGlasses();

    @GET("list.php?a=list")
    Observable<DrinkList> getAlcoholic();

    @GET("list.php?i=list")
    Observable<DrinkList> getIngredients();
}
