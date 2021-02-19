package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    public LottieAnimationView lottieAnimationView;
    public Button pause;
    public int isRunning = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottieAnimationView = findViewById(R.id.animation);
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(v -> {
            if(isRunning == 0)
            {
                lottieAnimationView.pauseAnimation();
                isRunning=1;
            }
            else
            {
                lottieAnimationView.resumeAnimation();
                isRunning=0;
            }
        });
    }
}