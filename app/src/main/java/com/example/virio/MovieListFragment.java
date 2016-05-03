package com.example.virio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virio.model.Movie;
import com.example.virio.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class MovieListFragment extends Fragment {
    @BindView(R.id.rvMovieList)
    protected RecyclerView rvMovieList;
    private MoviesAdapter moviesAdapter;
    private final List<Movie> movies = new ArrayList<>();

    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    public void setMovies(List<Movie> movies, MovieSelectedListener movieSelectedListener) {
        this.movies.clear();
        this.movies.addAll(movies);
        int screenWidth = ScreenUtil.getScreenWidth(getActivity());
        moviesAdapter = new MoviesAdapter(getContext(), movieSelectedListener, screenWidth);
        moviesAdapter.setMovies(movies);
        rvMovieList.setAdapter(moviesAdapter);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}