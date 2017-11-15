package com.example.azram.giphysearch.giphydetails;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.azram.giphysearch.model.Gif;

import io.realm.Realm;

public class GiphyDetailsPresenter implements GiphyDetailsContract.GiphyDetailsPresenter {

    private Realm realm;

    private String gifId;

    private GiphyDetailsContract.GiphyDetailsView mGiphyDetailsView;

    GiphyDetailsPresenter(GiphyDetailsContract.GiphyDetailsView giphyDetailsView, String gifId) {
        realm = Realm.getDefaultInstance();
        this.gifId = gifId;
        this.mGiphyDetailsView = giphyDetailsView;
        mGiphyDetailsView.setPresenter(this);
    }

    @Override
    public void bind() {
        loadGif();
    }

    private void loadGif() {
        if (TextUtils.isEmpty(gifId)) {
            mGiphyDetailsView.showGifNotFound();
            return;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                Gif gif = realm.where(Gif.class).equalTo("id", gifId).findFirst();

                if (gif != null) {
                    mGiphyDetailsView.showGif(gif);
                }
                else {
                    mGiphyDetailsView.showGifNotFound();
                }
            }
        });

    }

    @Override
    public void unbind() {
        if (realm != null) {
            realm.close();
        }
        this.mGiphyDetailsView = null;
    }
}
