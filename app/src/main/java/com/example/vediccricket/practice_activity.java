package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class practice_activity extends AppCompatActivity implements PracticeAdapter.OnClickListenerInterface {

    public RecyclerView recyclerView;
    public ArrayList<PracticeModel> Topics = new ArrayList();
    public static HashMap<String, Boolean> isLearned = new HashMap<>();
    public TextView coins;
    public TextView level;
    public TextView currentTopic;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        coins.setText(String.valueOf(User.coins));
        level.setText("Level : "+User.level);
        currentTopic.setText(Topics.get(User.level).getTopicName());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_activity);

        Log.d("USER_1", User.name+" "+User.coins+" "+User.level);

        coins = findViewById(R.id.RewardCoins);
        coins.setText(String.valueOf(User.coins));
        recyclerView = findViewById(R.id.recyclerView);
        level = findViewById(R.id.level);
        currentTopic = findViewById(R.id.currentTopic);
        level.setText("Level :"+User.level);

        Topics.add(new PracticeModel("Multiplication", 1, 10, false));
        Topics.add(new PracticeModel("Division", 2, 20, false));
        Topics.add(new PracticeModel("Powers", 3, 30, false));
        Topics.add(new PracticeModel("Ch 1", 4, 40, false));
        Topics.add(new PracticeModel("Ch 2", 5, 50, false));
        Topics.add(new PracticeModel("Ch 3", 6, 60, false));
        Topics.add(new PracticeModel("Ch 4", 7, 70, false));
        Topics.add(new PracticeModel("Ch 5", 8, 80, false));
        Topics.add(new PracticeModel("Ch 6", 9, 90, false));
        Topics.add(new PracticeModel("Ch 7", 10, 100, false));
        Topics.add(new PracticeModel("Ch 8", 11, 110, false));
        Topics.add(new PracticeModel("Ch 9", 12, 120, false));
        recyclerView.setAdapter(new PracticeAdapter(Topics, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentTopic.setText(Topics.get(Common.maxLvl).getTopicName());
        // get it from firebase
        for(PracticeModel e: Topics)
        {
            isLearned.put(e.getTopicName(), e.isLearned());
        }
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
    public void onClick(int position) {
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("Topic", Topics.get(position).getTopicName());
        intent.putExtra("Reward", Topics.get(position).getRewardCoins());
        if(Topics.get(position).getLevel()-1 > User.level)
        {
            int maxLvl = User.level+1;
            Toast.makeText(practice_activity.this, "Please complete level "+maxLvl, Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }
}