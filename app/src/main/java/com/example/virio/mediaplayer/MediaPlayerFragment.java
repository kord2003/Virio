package com.example.virio.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.virio.PlaybackListener;
import com.example.virio.PlayerView;
import com.example.virio.R;
import com.example.virio.model.Movie;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class MediaPlayerFragment extends Fragment implements SurfaceHolder.Callback, View.OnClickListener, PlayerView {
    private static final String TAG = MediaPlayerFragment.class.getName();
    private MediaPlayer mediaPlayer;
    @BindView(R.id.surfaceView)
    protected SurfaceView surfaceView;
    @BindView(R.id.btnPP)
    protected ImageView btnPP;
    private SurfaceHolder holder;
    public static String filepath;
    private PlaybackListener playbackListener;

    private boolean isPaused = false;
    private boolean isSurfaceDestroyed = false;
    private boolean isSurfaceReattachRequired = false;
    private int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_media_player, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared: " + mp.getVideoWidth() + ", mediaPlayer.isPlaying(): " + mediaPlayer.isPlaying());
                playbackListener.onDurationChanged(mp.getDuration());
                handleAspectRatio();

                if (!isSurfaceDestroyed) {
                    mediaPlayer.seekTo(currentPosition);
                    resumePlayback();
                    isSurfaceDestroyed = false;
                    isSurfaceReattachRequired = false;
                } else {
                    pausePlayback();
                }
            }
        });
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        Log.d(TAG, "onInfo: MEDIA_INFO_BUFFERING_START");
                        return true;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        Log.d(TAG, "onInfo: MEDIA_INFO_BUFFERING_END");
                        return true;
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        Log.d(TAG, "onInfo: MEDIA_INFO_VIDEO_RENDERING_START");
                        playbackListener.onPlaybackStart();
                        return true;
                }
                return false;
            }
        });
    }

    public void onPause() {
        super.onPause();
        pausePlayback();
        currentPosition = mediaPlayer.getCurrentPosition();
        Log.d(TAG, "onPause currentPosition = " + currentPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume currentPosition = " + currentPosition);
        if (isSurfaceDestroyed) {
            isSurfaceReattachRequired = true;
        } else {
            resumePlayback();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: isSurfaceDestroyed = " + isSurfaceDestroyed + ", isSurfaceReattachRequired = " + isSurfaceReattachRequired);
        mediaPlayer.setDisplay(holder);
        if (isSurfaceDestroyed && isSurfaceReattachRequired) {
            mediaPlayer.seekTo(currentPosition);
            resumePlayback();
            isSurfaceDestroyed = false;
            isSurfaceReattachRequired = false;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        isSurfaceDestroyed = true;
    }

    @Override
    public void setPlaybackListener(PlaybackListener playbackListener) {
        this.playbackListener = playbackListener;
    }

    @Override
    public void loadMovie(Movie movie) {
        filepath = movie.getStreams().getUrl();
        Log.d(TAG, "loadMovie: " + filepath);
        try {
            resetMediaPlayer();
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetMediaPlayer() {
        mediaPlayer.reset();
        btnPP.setImageResource(R.drawable.pause_outline);
        isPaused = false;
        isSurfaceDestroyed = false;
        isSurfaceReattachRequired = false;
        currentPosition = 0;
    }

    private void handleAspectRatio() {
        int surfaceViewWidth = surfaceView.getWidth();
        int surfaceViewHeight = surfaceView.getHeight();

        float videoWidth = mediaPlayer.getVideoWidth();
        float videoHeight = mediaPlayer.getVideoHeight();

        float ratioWidth = surfaceViewWidth / videoWidth;
        float ratioHeight = surfaceViewHeight / videoHeight;
        float aspectRatio = videoWidth / videoHeight;

        ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();

        if (ratioWidth > ratioHeight) {
            layoutParams.width = (int) (surfaceViewHeight * aspectRatio);
            layoutParams.height = surfaceViewHeight;
        } else {
            layoutParams.width = surfaceViewWidth;
            layoutParams.height = (int) (surfaceViewWidth / aspectRatio);
        }

        surfaceView.setLayoutParams(layoutParams);
    }

    @OnClick({R.id.btnPP})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPP:
                isPaused = !isPaused;
                if (isPaused) {
                    pausePlayback();
                } else {
                    resumePlayback();
                }
                break;
        }
    }

    private void resumePlayback() {
        Log.d(TAG, "resumePlayback");
        btnPP.setImageResource(R.drawable.pause_outline);
        mediaPlayer.start();
    }

    private void pausePlayback() {
        Log.d(TAG, "pausePlayback");
        btnPP.setImageResource(R.drawable.play_outline);
        mediaPlayer.pause();
    }
}