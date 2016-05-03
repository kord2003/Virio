package com.example.virio.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class Meta {
    @SerializedName("releaseYear")
    private String releaseYear;

    @SerializedName("directors")
    private List<Person> directors;

    @SerializedName("actors")
    private List<Person> actors;

    public String getReleaseYear() {
        return releaseYear;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public List<Person> getActors() {
        return actors;
    }
}