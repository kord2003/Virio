package com.example.virio;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.virio.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView {
    private Presenter presenter;
    @BindView(R.id.containerLoadingCover)
    protected View containerLoadingCover;
    private boolean isFirstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new Presenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach(this);
        if (isFirstLaunch) {
            presenter.loadMoviesFromAssets(this);
            isFirstLaunch = false;
        }
        presenter.tryLoadCurrentMovie(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detach();
    }

    @Override
    public void showProgress() {
        containerLoadingCover.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        containerLoadingCover.setVisibility(View.GONE);
    }

    @Override
    public void setMovies(List<Movie> movies, MovieSelectedListener movieSelectedListener) {
        MovieListFragment movieListFragment = getMovieListFragment();
        if (movieListFragment != null) {
            movieListFragment.setMovies(movies, movieSelectedListener);
        }
    }

    @Override
    public void loadMovie(Movie movie, PlaybackListener playbackListener) {
        MovieLoadingCoverFragment movieLoadingCoverFragment = getMovieLoadingCoverFragment();
        if (movieLoadingCoverFragment != null) {
            movieLoadingCoverFragment.setMovie(movie);
        }

        PlayerView playerFragment = getPlayerView();
        if (playerFragment != null) {
            playerFragment.setPlaybackListener(playbackListener);
            playerFragment.loadMovie(movie);
        }
    }

    @Override
    public void setDuration(long duration) {
        MovieLoadingCoverFragment movieLoadingCoverFragment = getMovieLoadingCoverFragment();
        if (movieLoadingCoverFragment != null) {
            movieLoadingCoverFragment.setDuration(duration);
        }
    }

    private MovieLoadingCoverFragment getMovieLoadingCoverFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (MovieLoadingCoverFragment) fm.findFragmentById(R.id.fragmentMovieLoadingCover);
    }

    private MovieListFragment getMovieListFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (MovieListFragment) fm.findFragmentById(R.id.fragmentMovieList);
    }

    private PlayerView getPlayerView() {
        FragmentManager fm = getSupportFragmentManager();
        return (PlayerView) fm.findFragmentById(R.id.fragmentPlayer);
    }
}