package id.daruanugerah.moviecataloguesub4.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import id.daruanugerah.moviecataloguesub4.activity.MainActivity;
import id.daruanugerah.moviecataloguesub4.api.ApiClient;
import id.daruanugerah.moviecataloguesub4.api.ApiService;
import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.model.CatalogResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Catalog>> catalogList = new MutableLiveData<>();
    private ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public void setCatalogs(final String catalogType) {

        Call<CatalogResponse> call = apiService.getCatalogs(catalogType);
        call.enqueue(new Callback<CatalogResponse>() {
            @Override
            public void onResponse(Call<CatalogResponse> call, Response<CatalogResponse> response) {
                try {
                    ArrayList<Catalog> catalogs = response.body().getResults();
                    for (Catalog data : catalogs) {
                        data.setCatalogType(catalogType.equals("movie") ? 1 : 2);
                    }
                    catalogList.postValue(catalogs);

                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<CatalogResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());

            }
        });
    }

    public LiveData<ArrayList<Catalog>> getCatalogs() {
        return catalogList;
    }

    public  void setFavCatalog (ArrayList<Catalog> catalogs) {
        catalogList.postValue(catalogs);
    }

    public void searchCatalog(String query) {
        Call<CatalogResponse> call = apiService.searchCatalogs(query);
        call.enqueue(new Callback<CatalogResponse>() {
            @Override
            public void onResponse(Call<CatalogResponse> call, Response<CatalogResponse> response) {
                try {
                    ArrayList<Catalog> catalogs = response.body().getResults();
                    catalogList.postValue(catalogs);
                    Log.d(MainActivity.class.getSimpleName(), catalogs.toString());

                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<CatalogResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }
}
