package com.example.virio;

import android.content.Context;
import android.util.Log;

import com.example.virio.model.Movie;
import com.example.virio.model.MovieCatalogParser;

import java.util.List;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class Presenter implements PlaybackListener, MovieSelectedListener {
    private static final String TAG = Presenter.class.getName();
    private NavigationView view;
    private List<Movie> movies;
    private Movie selectedMovie;
    private boolean isPlaybackStarted = false;

    public void attach(NavigationView view) {
        this.view = view;
        if(view != null) {
            if (isPlaybackStarted) {
                view.hideProgress();
            } else {
                view.showProgress();
            }
        }
    }

    public void detach() {
        view = null;
    }

    public void loadMoviesFromAssets(Context context) {
        MovieCatalogParser parser = new MovieCatalogParser(context);
        movies = parser.getMovieCatalog();
        if (view != null) {
            view.setMovies(movies, this);
            if (selectedMovie == null) {
                selectedMovie = movies.get(BuildConfig.INITIAL_MOVIE);
            }
        }
    }

    @Override
    public void onPlaybackStart() {
        if (view != null) {
            view.hideProgress();
            isPlaybackStarted = true;
        }
    }

    @Override
    public void onDurationChanged(long duration) {
        if (view != null) {
            view.setDuration(duration);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        Log.d(TAG, "onMovieSelected: " + movie.getTitle());
        selectedMovie = movie;
        tryLoadCurrentMovie(true);
    }

    public void tryLoadCurrentMovie(boolean forceLoad) {
        if (view != null && (forceLoad || !isPlaybackStarted)) {
            isPlaybackStarted = false;
            view.setDuration(0);
            view.showProgress();
            view.loadMovie(selectedMovie, this);
        }
    }
}