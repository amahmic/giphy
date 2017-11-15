package com.example.azram.giphysearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class GifAnswer {

    @Expose(serialize = true, deserialize = false)
    private String query;

    @SerializedName("data")
    @Expose
    private RealmList<Gif> gifs;

    public RealmList<Gif> getListaGifova() {
        return gifs;
    }

    public void setListaGifova(RealmList<Gif> gif) {
        this.gifs = gif;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
