package id.daruanugerah.moviecataloguesub4.api;

import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.model.CatalogResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/{type}")
    Call<CatalogResponse> getCatalogs(@Path("type") String catalogType);

    @GET("search/multi")
    Call<CatalogResponse> searchCatalogs(@Query("query") String query);

    @GET("discover/movie")
    Call<CatalogResponse> getReleasedCatalog(@Query("primary_release_date.gte") String date, @Query("primary_release_date.lte") String today);
}
