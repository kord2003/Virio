package com.example.virio;

import com.example.virio.model.Movie;

/**
 * Created by sharlukovich on 03.05.2016.
 */
public interface PlayerView {
    void setPlaybackListener(PlaybackListener playbackListener);

    void loadMovie(Movie movie);
}