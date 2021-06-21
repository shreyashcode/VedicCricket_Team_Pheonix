package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vediccricket.Service.BackgroundMusicService;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView practice;
    private ImageView tournament;
    private ImageView leaderboard;
    private Intent intent;
    private Intent musicIntent;
    private MediaPlayer mediaPlayer;
    private MediaPlayer backgroundMusic;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SHREYASH1", "ONSTART");
//        Toast.makeText(this, "ONSTART", Toast.LENGTH_SHORT).show();
//        startService(new Intent(this, BackgroundMusicService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SHREYASH1: ", "ONSTOP");
//        Toast.makeText(this, "ONSTOP", Toast.LENGTH_SHORT).show();
//        stopService(new Intent(this, BackgroundMusicService.class));
    }

    public void onResume() {
        super.onResume();
        Log.d("SHREYASH1:", "ONRESUME");
        startService(musicIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SHREYASH1", "ONPAUSE");
//        Toast.makeText(this, "OnPause: ", Toast.LENGTH_SHORT).show();
        stopService(musicIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(this, "STARTED", Toast.LENGTH_SHORT).show();
//        startService(new Intent(this, BackgroundMusicService.class));
        musicIntent = new Intent(this, BackgroundMusicService.class);

        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        backgroundMusic = MediaPlayer.create(this, R.raw.backgmusic);
//        backgroundMusic.start();

        practice = findViewById(R.id.practice);
        tournament = findViewById(R.id.tournament);
        leaderboard = findViewById(R.id.leaderboard);

        practice.setOnClickListener(this);
        tournament.setOnClickListener(this);
        leaderboard.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        mediaPlayer.start();
        switch(v.getId()){
            case R.id.practice:
                    intent = new Intent(HomeActivity.this, practice_activity.class);
                    break;

            case R.id.tournament:
                    intent = new Intent(HomeActivity.this, MainGameActivity.class);
                    break;

            case R.id.leaderboard:
                    intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
                    break;
        }
//        Common.seekTo = backgroundMusic.getCurrentPosition();
//        backgroundMusic.stop();
        startActivity(intent);
        overridePendingTransition(R.anim.down_animation, R.anim.up_animation);
    }
}