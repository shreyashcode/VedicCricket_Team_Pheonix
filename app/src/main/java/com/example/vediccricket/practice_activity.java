package com.example.vediccricket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vediccricket.Service.BackgroundMusicService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class practice_activity extends AppCompatActivity implements PracticeAdapter.OnClickListenerInterface {

    public RecyclerView recyclerView;
    public ArrayList<PracticeModel> Topics = new ArrayList<>();
    public static HashMap<String, Boolean> isLearned = new HashMap<>();
    public TextView coins;
    public TextView level;
    public TextView currentTopic;
    public PracticeAdapter practiceAdapter;
    public FirebaseFirestore firebaseFirestore;
    public ProgressBar progressBar;
    public Intent musicIntent;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        progressBar.setVisibility(View.VISIBLE);
        populateTopics();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_activity);
        musicIntent = new Intent(this, BackgroundMusicService.class);
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.loading);

        ImageView imageView = findViewById(R.id.back);
        coins = findViewById(R.id.RewardCoins);
        recyclerView = findViewById(R.id.recyclerView);
        level = findViewById(R.id.level);
        currentTopic = findViewById(R.id.currentTopic);

        imageView.setOnClickListener(v->{
                finish();
                overridePendingTransition(R.anim.down_animation, R.anim.up_animation);
        });

        populateTopics();

        for(PracticeModel e: Topics) {
            isLearned.put(e.getTopicName(), e.isLearned());
        }
    }

    public void populateTopics(){
        CollectionReference collectionReference = firebaseFirestore.collection("topics");
        collectionReference.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Topics = new ArrayList<>();
                        for(QueryDocumentSnapshot document: task.getResult()){
                            PracticeModel practice_ = new PracticeModel(document.get("topic_name").toString(),
                                    Integer.parseInt(document.get("level").toString()),
                                    Integer.parseInt(document.get("level").toString()),
                                    false);
                            if(User.level >= practice_.level){
                                practice_.isLearned = true;
                            }
                            Log.d("SHREYASH: ", practice_.toString());

                            Topics.add(practice_);
                        }
                        Collections.sort(Topics, (o1, o2) -> o1.level-o2.level);
                        coins.setText(String.valueOf(User.coins));
                        level.setText("Level : "+User.level);
                        currentTopic.setText(Topics.get(User.level).getTopicName());
                        progressBar.setVisibility(View.GONE);
                        practiceAdapter = new PracticeAdapter(Topics, this);
                        recyclerView.setAdapter(practiceAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        practiceAdapter.notifyDataSetChanged();
                        Log.d("SHREYASH:", "COMPLETED");
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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("Topic", Topics.get(position).getTopicName());
        intent.putExtra("Reward", Topics.get(position).getRewardCoins());

        if(Topics.get(position).getLevel()-1 > User.level) {
            int maxLvl_ = User.level+1;
            Toast.makeText(practice_activity.this, "Please complete level "+maxLvl_, Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }

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
}