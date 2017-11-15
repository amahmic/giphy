package com.example.azram.giphysearch.api;

import com.example.azram.giphysearch.api.services.SearchGiphyService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient INSTANCE;
    private SearchGiphyService mSearchGiphyService;

    public static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }

        return INSTANCE;
    }

    private RetrofitClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient searchQueryHttpClient = okHttpClient.newBuilder()
                .addInterceptor(new SearchGiphyService.SearchGiphyInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(searchQueryHttpClient.newBuilder().addInterceptor(logging).build())
                .baseUrl(SearchGiphyService.SEARCH_GIPHY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mSearchGiphyService = retrofit.create(SearchGiphyService.class);
    }

    public SearchGiphyService getSearchGiphyService() {
        return mSearchGiphyService;
    }

}
