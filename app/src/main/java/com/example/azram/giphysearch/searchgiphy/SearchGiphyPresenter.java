package com.example.azram.giphysearch.searchgiphy;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.azram.giphysearch.api.RetrofitClient;
import com.example.azram.giphysearch.api.services.SearchGiphyService;
import com.example.azram.giphysearch.model.Gif;
import com.example.azram.giphysearch.model.GifAnswer;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchGiphyPresenter implements SearchGiphyContract.SearchGiphyPresenter {

    private static final String LOG_TAG = SearchGiphyPresenter.class.getName();

    private SearchGiphyContract.SearchGiphyView mSearchGiphyView;

    private Realm realm;

    private String mLastSearchQuery = "";

    private Call<GifAnswer> listaGifova;

    SearchGiphyPresenter(SearchGiphyContract.SearchGiphyView searchGiphyView) {
        mSearchGiphyView = searchGiphyView;
        realm = Realm.getDefaultInstance();
        searchGiphyView.setPresenter(this);
        searchGiphyView.setAdapter(realm.where(Gif.class).findAll());
    }

    @Override
    public void bind() {
    }

    @Override
    public void searchQueryChanged(final String searchQuery) {

        if (mLastSearchQuery.equals(searchQuery)) {
            //searchQuery metoda se pozvala sa istim search query - em, vec loadao podatke
            return;
        }

        //Izbrisi sve iz realma - pocinjemo novu pretragu
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.deleteAll();
            }
        });

        if (TextUtils.isEmpty(searchQuery)) {
            return;
        }

        String trimmedSearchQuery = searchQuery.trim();

        loadGifs(trimmedSearchQuery);
        mLastSearchQuery = trimmedSearchQuery;
    }

    private void loadGifs(final String searchQuery) {

        //U suprotnom load gifove
        mSearchGiphyView.setLoadingIndicator(true);

        SearchGiphyService searchGiphyService = RetrofitClient.getInstance()
                .getSearchGiphyService();

        listaGifova = searchGiphyService.getGifs(searchQuery);

        listaGifova.enqueue(new Callback<GifAnswer>() {
            @Override
            public void onResponse(@NonNull Call<GifAnswer> call, @NonNull final Response<GifAnswer> response) {

                if (mSearchGiphyView == null) {
                    return;
                }

                if (response.code() != 200) {
                    mSearchGiphyView.showLoadingGifsError();
                    return;
                }

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        //Ako nije uspio izvrsiti delete, delete ovdje
                        if (realm.where(Gif.class).findAll().size() != 0) {
                            realm.deleteAll();
                        }
                        GifAnswer gifAnswer = response.body();
                        if (gifAnswer != null && !gifAnswer.getListaGifova().isEmpty()) {
                            realm.insertOrUpdate(gifAnswer.getListaGifova());
                        }
                        else {
                            mSearchGiphyView.showNoGifs();
                        }
                    }
                });

                mSearchGiphyView.setLoadingIndicator(false);
            }

            @Override
            public void onFailure(@NonNull Call<GifAnswer> call, @NonNull Throwable t) {

                if (mSearchGiphyView == null) {
                    return;
                }

                mSearchGiphyView.setLoadingIndicator(false);
                mSearchGiphyView.showLoadingGifsError();
            }
        });
    }

    @Override
    public void unbind() {
        if (realm != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.deleteAll();
                }
            });
            realm.close();
        }

        if (listaGifova != null && listaGifova.isExecuted()) {
            listaGifova.cancel();
        }

        this.mSearchGiphyView = null;
    }
}
