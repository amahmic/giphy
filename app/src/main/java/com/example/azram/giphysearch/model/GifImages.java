package com.example.azram.giphysearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class GifImages extends RealmObject{

    //@SerializedName("original_still")
    @SerializedName("fixed_height_still")
    @Expose
    private GifImage gifImageStill;

    //@SerializedName("original")
    //@SerializedName("original")
    @SerializedName("fixed_height")
    @Expose
    private GifImage gifImage;

    public GifImage getGifImageStill() {
        return gifImageStill;
    }

    public void setGifImageStill(GifImage gifImageStill) {
        this.gifImageStill = gifImageStill;
    }

    public GifImage getGifImage() {
        return gifImage;
    }

    public void setOriginalGif(GifImage gifImage) {
        this.gifImage = gifImage;
    }
}
