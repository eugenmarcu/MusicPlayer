package com.example.android.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class NowPlayingActivity extends AppCompatActivity {

    private int progress = 0;
    private SeekBar progressBar;
    private boolean playing = false;
    private TextView timeCurrent;
    private TextView timeEnd;
    private int songDuration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String artist = intent.getExtras().getString("artist");
        String duration = intent.getExtras().getString("duration");
        songDuration = getDuration(duration);

        //Set the song title and artist
        TextView textView = findViewById(R.id.now_playing_text);
        textView.setText(title + " - " + artist);
        //Set the times
        timeCurrent = findViewById(R.id.now_playing_time);
        timeCurrent.setText(showTime(progress));
        timeEnd = findViewById(R.id.now_playing_time_end);
        timeEnd.setText(duration);

        progressBar = findViewById(R.id.now_playing_progress);
        progressBar.setMax(songDuration);

        //On Play button
        final ImageView playBtn = findViewById(R.id.now_playing_play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NowPlayingActivity.this, "Play", Toast.LENGTH_SHORT).show();
                if (!playing) {
                    playing = true;
                    setProgressValue();
                }
            }
        });

        ImageView image = findViewById(R.id.now_playing_image);
        image.setImageResource(R.drawable.music1);

        //On Pause Button
        ImageView pauseBtn = findViewById(R.id.now_playing_pause);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NowPlayingActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                if (playing) {
                    playing = false;
                }
            }
        });

        //On Stop Button
        ImageView stopBtn = findViewById(R.id.now_playing_stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NowPlayingActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                playing = false;
                progress = 0;
                progressBar.setProgress(progress);
                timeCurrent.setText(showTime(progress));
            }
        });

        //on Progress changed
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentProgress, boolean fromUser) {
                progress = currentProgress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //show time format from int
    private String showTime(int progress) {
        int min = progress / 60;
        int sec = progress % 60;
        String result = String.valueOf(min) + ":";
        if (sec < 10)
            result += "0" + String.valueOf(sec);
        else
            result += String.valueOf(sec);

        return result;
    }

    //get duration from time format
    private int getDuration(String duration) {
        String[] time = duration.split(":");
        int result = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
        return result;
    }

    //set the seekBar progress on play
    private void setProgressValue() {
        // set the progress
        progressBar.setProgress(progress);
        // thread is used to change the progress value
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (playing && progress < songDuration) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timeCurrent.setText(showTime(progress));
                        }
                    });

                    progress++;
                    setProgressValue();
                }
            }
        });
        thread.start();
    }
}
