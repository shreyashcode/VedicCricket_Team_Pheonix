package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;

import static android.view.View.VISIBLE;

public class TutorialActivity extends AppCompatActivity {

    private VideoView videoView;
    private CardView collect;
    private MediaController mediaController;
    private String TopicName;
    private int coins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        TopicName = getIntent().getStringExtra("Topic");
        coins = getIntent().getIntExtra("Reward", 0);
        Toast.makeText(TutorialActivity.this, TopicName+" "+coins, Toast.LENGTH_SHORT).show();
        videoView = findViewById(R.id.video);
        collect = findViewById(R.id.collect);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video1);
        videoView.start();
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(mp -> collect.setVisibility(VISIBLE));

        videoView.setOnPreparedListener(mp -> Toast.makeText(TutorialActivity.this, "Length: "+videoView.getDuration(), Toast.LENGTH_SHORT).show());

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TutorialActivity.this, "Collected", Toast.LENGTH_SHORT).show();
                //update the activity on firebase
                if(practice_activity.isLearned.get(TopicName) == false)
                {
                    Log.d("Tutorial", "Learned "+TopicName+" "+Common.coins+coins);
                    Common.coins = Common.coins+coins;
                    Common.maxLvl++;
                    practice_activity.isLearned.put(TopicName, true);
                }
                else
                {
                    Log.d("Tutorial", "Not learned "+TopicName);
                }
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}