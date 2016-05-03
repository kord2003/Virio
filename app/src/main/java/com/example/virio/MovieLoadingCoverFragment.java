package com.example.virio;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.virio.model.Movie;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class MovieLoadingCoverFragment extends Fragment {

    @BindView(R.id.tvTitle)
    protected TextView tvTitle;
    @BindView(R.id.tvYear)
    protected TextView tvYear;
    @BindView(R.id.tvDuration)
    protected TextView tvDuration;
    @BindView(R.id.tvDescription)
    protected TextView tvDescription;
    @BindView(R.id.ivCover)
    protected ImageView ivCover;
    @BindView(R.id.pbLoading)
    protected ProgressBar pbLoading;

    public MovieLoadingCoverFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_loading_cover, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    public void setMovie(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvYear.setText(movie.getMeta().getReleaseYear());
        tvDescription.setText(movie.getDescription());
        Uri imageUri = Uri.parse("file:///android_asset/" + movie.getImages().getPlaceholder());
        if (getContext() != null) {
            Glide.with(getContext())
                    .load(imageUri)
                    .into(ivCover);
        }
    }

    public void setDuration(long duration) {
        if(duration > 0) {
            String formattedDuration = getFormattedDuration(duration);
            tvDuration.setText(formattedDuration);
            tvDuration.setVisibility(View.VISIBLE);
        } else {
            tvDuration.setVisibility(View.GONE);
        }
    }

    private String getFormattedDuration(long duration) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }
}