package com.example.virio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class Stream {
    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}