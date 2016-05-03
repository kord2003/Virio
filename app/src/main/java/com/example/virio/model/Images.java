package com.example.virio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class Images {
    @SerializedName("cover")
    private String cover;

    @SerializedName("placeholder")
    private String placeholder;

    public String getCover() {
        return cover;
    }

    public String getPlaceholder() {
        return placeholder;
    }
}