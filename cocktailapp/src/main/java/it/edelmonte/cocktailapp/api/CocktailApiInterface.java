package it.edelmonte.cocktailapp.api;

import io.reactivex.rxjava3.core.Observable;
import it.edelmonte.cocktailapp.model.CocktailList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CocktailApiInterface {

    @GET("list.php?c=list")
    Observable<CocktailList> getCategories();

    @GET("list.php?g=list")
    Observable<CocktailList> getGlasses();

    @GET("list.php?a=list")
    Observable<CocktailList> getAlcoholic();

    @GET("list.php?i=list")
    Observable<CocktailList> getIngredients();

    @GET
    Call<CocktailList> getCocktails(@Url String endpoint);
}
