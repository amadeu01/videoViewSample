package io.github.amadeu01.videoviewdemo;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog dialog;
    private VideoView videoView;
    private ImageButton playButton;

    private String videoURL = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.video_view);
        playButton = findViewById(R.id.btn_play_pause);
        playButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait.... loading video...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        try {
            if (!videoView.isPlaying()) {
                Uri uri = Uri.parse(videoURL);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        playButton.setImageResource(R.drawable.ic_play);
                    }
                });
            } else {
                videoView.pause();
                playButton.setImageResource(R.drawable.ic_play);
                dialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                dialog.dismiss();
                mediaPlayer.setLooping(true);
                videoView.start();
                playButton.setImageResource(R.drawable.ic_pause);
            }
        });
    }
}
