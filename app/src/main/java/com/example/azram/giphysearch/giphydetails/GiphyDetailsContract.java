package com.example.azram.giphysearch.giphydetails;

import com.example.azram.giphysearch.model.Gif;
import com.example.azram.giphysearch.util.BasePresenter;
import com.example.azram.giphysearch.util.BaseView;

public class GiphyDetailsContract {

    interface GiphyDetailsView extends BaseView<GiphyDetailsPresenter> {
        void showGif(Gif gif);
        void showGifNotFound();
    }

    interface GiphyDetailsPresenter extends BasePresenter {

    }
}
