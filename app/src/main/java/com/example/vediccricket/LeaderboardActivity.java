package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {
    public RecyclerView leader_rv;
    public FirebaseFirestore firebaseFirestore;
    public DBAdapter adapter;
    public FirestoreRecyclerOptions<Player> response;

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        setLeaderRView();
//        Toast.makeText(this, "RESUME", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        leader_rv = findViewById(R.id.leaderboard_rView);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setLeaderRView();

//
//            adapter = new FirestoreRecyclerAdapter<Player, LeaderboardViewHolder>(response) {
//            @Override
//            public void onBindViewHolder(LeaderboardViewHolder holder, int position, Player player) {
//                Log.d("BJP: ", player.coins+" "+player.userName);
//                holder.name.setText(player.userName);
//                holder.coins.setText(String.valueOf(player.coins));
//            }
//            @Override
//            public LeaderboardViewHolder onCreateViewHolder(ViewGroup group, int i) {
//                View view = LayoutInflater.from(group.getContext())
//                        .inflate(R.layout.leaderboard_item, group, false);
//                return new LeaderboardViewHolder(view);
//            }
//            @Override
//            public void onError(FirebaseFirestoreException e) {
//                Log.e("error", e.getMessage());
//            }
//        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void setLeaderRView(){
        Query query = firebaseFirestore.collection("leaderboard")
                .orderBy("coins", Query.Direction.DESCENDING)
                .limit(15);

        response = new FirestoreRecyclerOptions.Builder<Player>()
                .setQuery(query, Player.class)
                .build();

        adapter = new DBAdapter(response);
        leader_rv.setAdapter(adapter);
        leader_rv.setHasFixedSize(true);
        leader_rv.setLayoutManager(new LinearLayoutManager(this));
//        ArrayList<Player> players = new ArrayList<>();
//        players.add(new Player("NAME0", 10, 1));
//        players.add(new Player("NAME1", 10, 1));
//        players.add(new Player("NAME2", 10, 1));
//        players.add(new Player("NAME0", 10, 1));
//        players.add(new Player("NAME1", 10, 1));
//        players.add(new Player("NAME2", 10, 1));
//        players.add(new Player("NAME0", 10, 1));
//        players.add(new Player("NAME1", 10, 1));
//        players.add(new Player("NAME2", 10, 1));
//        LeaderboardAdapter adapter = new LeaderboardAdapter(players);
//        leader_rv.setAdapter(adapter);
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