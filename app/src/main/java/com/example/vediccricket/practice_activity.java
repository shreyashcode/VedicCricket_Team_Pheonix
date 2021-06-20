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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.loading);

        coins = findViewById(R.id.RewardCoins);
        recyclerView = findViewById(R.id.recyclerView);
        level = findViewById(R.id.level);
        currentTopic = findViewById(R.id.currentTopic);

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
}