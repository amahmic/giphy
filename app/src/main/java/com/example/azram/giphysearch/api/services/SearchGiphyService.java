package com.example.azram.giphysearch.api.services;

import android.support.annotation.NonNull;

import com.example.azram.giphysearch.model.GifAnswer;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchGiphyService {

    String SEARCH_GIPHY_BASE_URL = "http://api.giphy.com/v1/";

    @GET("gifs/search")
    Call<GifAnswer> getGifs(@Query("q") String searchQuery);

    public class SearchGiphyInterceptor implements Interceptor {

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            //Dodaj API_KEY na svaki request
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    //TODO: API_KEY
                    .addQueryParameter("apikey", "")
                    //.addQueryParameter("offset", "20")
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
