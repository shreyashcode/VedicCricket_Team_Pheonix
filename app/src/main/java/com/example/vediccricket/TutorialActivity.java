package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.VISIBLE;

public class TutorialActivity extends AppCompatActivity {

    private VideoView videoView;
    private CardView collect;
    private MediaController mediaController;
    private String TopicName;
    private int coins;
    private TextView topic_name;
    private LottieAnimationView coinsReward;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        coinsReward = findViewById(R.id.coinsReward);
        topic_name = findViewById(R.id.topic_name_view);
        topic_name.setText(TopicName);

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

        videoView.setOnClickListener(v->{
            if(topic_name.getVisibility() == VISIBLE){
                topic_name.setVisibility(View.INVISIBLE);
            }else{
                topic_name.setVisibility(View.INVISIBLE);
            }
        });

        videoView.setOnCompletionListener(mp -> {
            videoView.setVisibility(View.INVISIBLE);
            if(User.topicsLearned.contains(TopicName) == false) {
                coinsReward.setVisibility(VISIBLE);
            }
            else{
                finish();
            }
            //coinsReward.setProgress(0);
            Log.d("HeyThis", "OnComplete");
        });

        coinsReward.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //update the activity on firebase
                if(User.topicsLearned.contains(TopicName) == false)
                {
                    User.coins = User.coins+coins;
                    User.level++;
                    User.topicsLearned.add(TopicName);
                    updateOnFirebase();
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void updateOnFirebase(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("name", TopicName);
        firebaseFirestore.collection("user").document(User.name).collection("topics_learned").document(TopicName)
                .set(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("coins", User.coins);
        map1.put("level", User.level);
        map1.put("name", User.name);
        firebaseFirestore.collection("user").document(User.name).set(map1);
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