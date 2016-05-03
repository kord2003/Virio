package com.example.virio;

import com.example.virio.model.Movie;

import java.util.List;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public interface NavigationView {
    void showProgress();
    void hideProgress();
    void setMovies(List<Movie> movies, MovieSelectedListener movieSelectedListener);
    void loadMovie(Movie movie, PlaybackListener playbackListener);
    void setDuration(long duration);
}
