package com.example.virio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class Movie {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("images")
    private Images images;

    @SerializedName("streams")
    private Stream streams;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Meta getMeta() {
        return meta;
    }

    public Images getImages() {
        return images;
    }

    public Stream getStreams() {
        return streams;
    }
}