package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainGameActivity extends AppCompatActivity {

    private TextView runs;
    private TextView rrr;
    private TextView overs;
    private TextView fours;
    private TextView sixes;
    private Button six;
    private Button four;
    private Button wicket;
    private int run_scored=0;
    private int wicket_=0;
    private int overs_=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);


        runs = findViewById(R.id.runs);
        rrr = findViewById(R.id.rrr);
        overs = findViewById(R.id.overs);
        fours = findViewById(R.id.fours);
        sixes = findViewById(R.id.sixes);

        six = findViewById(R.id.six);
        four = findViewById(R.id.four);
        wicket = findViewById(R.id.wicket);

        six.setOnClickListener(v->{
            run_scored = run_scored+6;
            overs_++;
            runs.setText(run_scored+"-"+wicket_);
            sixes.setText("SIX "+run_scored/6+"");
            overs.setText("OVERS "+overs_);
        });

        four.setOnClickListener(v->{
            run_scored = run_scored+4;
            overs_++;
            runs.setText(run_scored+"-"+wicket_);
            overs.setText("OVERS "+overs_);
            fours.setText("FOURS "+ run_scored/4+"");
        });

        wicket.setOnClickListener(v->{
            wicket_++;
            overs_++;
            runs.setText(run_scored+"-"+wicket_);
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