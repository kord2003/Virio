package com.example.virio;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceScreen;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.virio.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private static final int VIEWS_COUNT = 3;
    private final int screenWidth;

    private final List<Movie> dataSet = new ArrayList<>();
    private final Context context;
    private MovieSelectedListener movieSelectedListener;

    public MoviesAdapter(Context context, MovieSelectedListener movieSelectedListener, int screenWidth) {
        this.context = context;
        this.movieSelectedListener = movieSelectedListener;
        this.screenWidth = screenWidth;
    }

    public void setMovies(List<Movie> movies) {
        dataSet.clear();
        dataSet.addAll(movies);
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int position) {
        Movie movie = dataSet.get(position);
        ImageView ivPreview = viewHolder.ivPreview;
        Uri imageUri = Uri.parse("file:///android_asset/" + movie.getImages().getCover());
        Glide.with(context)
                .load(imageUri)
                .into(ivPreview);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivPreview;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPreview = (ImageView) itemView.findViewById(R.id.ivPreview);
            itemView.setOnClickListener(this);
            itemView.getLayoutParams().width = screenWidth / VIEWS_COUNT;
        }

        @Override
        public void onClick(View v) {
            if (movieSelectedListener != null) {
                movieSelectedListener.onMovieSelected(dataSet.get(getPosition()));
            }
        }
    }
}