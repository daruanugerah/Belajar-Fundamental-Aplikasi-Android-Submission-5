package id.daruanugerah.moviecataloguesub4.api;

import android.util.Log;

import java.io.IOException;

import id.daruanugerah.moviecataloguesub4.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oriRequest = chain.request();
                HttpUrl httpUrl = oriRequest.url()
                        .newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build();

                oriRequest = oriRequest.newBuilder().url(httpUrl).build();

                //Log.d("oriRequest", String.valueOf(oriRequest));

                return chain.proceed(oriRequest);


            }
        }).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
