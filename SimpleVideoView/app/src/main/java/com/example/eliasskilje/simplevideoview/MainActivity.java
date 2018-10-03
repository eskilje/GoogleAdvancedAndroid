package com.example.eliasskilje.simplevideoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private static final String VIDEO_SAMPLE =
            "https://developers.google.com/training/images/tacoma_narrows.mp4";
    private VideoView videoView;
    private TextView bufferingTextView;
    private Button pausePlay;
    private Button advance;


    private static final String PLAYBACK_TIME = "play_time";
    private int currentPosition = 0;
    private boolean playing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoview);
        bufferingTextView = findViewById(R.id.buffering_textview);
        pausePlay = findViewById(R.id.pause_play);
        advance = findViewById(R.id.advance_video);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }

    private void initializePlayer() {
        bufferingTextView.setVisibility(VideoView.VISIBLE);
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        bufferingTextView.setVisibility(VideoView.INVISIBLE);

                        if (currentPosition > 0) {
                            videoView.seekTo(currentPosition);
                        } else {
                            videoView.seekTo(1);
                        }
                        pausePlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(playing) {
                                    pausePlay.setText(R.string.play);
                                    videoView.pause();
                                    playing = false;
                                }
                                else {
                                    pausePlay.setText(R.string.pause);
                                    videoView.start();
                                    playing = true;
                                }
                            }
                        });
                        advance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                videoView.seekTo(videoView.getCurrentPosition() + 10000);
                            }
                        });
                    }
                });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "Playback completed",
                        Toast.LENGTH_SHORT).show();
                videoView.seekTo(1);
            }
        });
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // media name is an external URL
            return Uri.parse(mediaName);
        } else { // media name is a raw resource embedded in the app
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }
}
