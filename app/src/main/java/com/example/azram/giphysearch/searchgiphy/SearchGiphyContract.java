package com.example.azram.giphysearch.searchgiphy;

import com.example.azram.giphysearch.model.Gif;
import com.example.azram.giphysearch.util.BasePresenter;
import com.example.azram.giphysearch.util.BaseView;

import io.realm.OrderedRealmCollection;

public interface SearchGiphyContract {

    interface SearchGiphyView extends BaseView<SearchGiphyPresenter> {
        void setAdapter(OrderedRealmCollection<Gif> listaGifova);
        void setLoadingIndicator(boolean active);
        void showNoGifs();
        void showLoadingGifsError();
    }

    interface SearchGiphyPresenter extends BasePresenter {
        void searchQueryChanged(String searchQuery);
    }
}
