package com.example.virio;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public interface PlaybackListener {
    void onPlaybackStart();
    void onDurationChanged(long duration);
}
