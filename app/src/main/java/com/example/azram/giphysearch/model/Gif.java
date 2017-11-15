package com.example.azram.giphysearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Gif extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("images")
    @Expose
    private GifImages gifImages;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GifImages getGifImages() {
        return gifImages;
    }

    public void setGifImages(GifImages gifImages) {
        this.gifImages = gifImages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Gif gif = (Gif) o;

        return id != null ? id.equals(gif.id) : gif.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}