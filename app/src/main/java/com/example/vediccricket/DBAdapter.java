package com.example.vediccricket;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DBAdapter extends FirestoreRecyclerAdapter<Player, DBAdapter.LeaderboardViewHolder> {

    public DBAdapter(@NonNull FirestoreRecyclerOptions<Player> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position, Player player) {
        Log.d("Sameer: ", player.name + " " + player.coins);
        holder.name.setText(player.name);
        holder.coins.setText(String.valueOf(player.coins));
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.leaderboard_item, parent, false);
                return new LeaderboardViewHolder(view);
    }

    class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView coins;

        public LeaderboardViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username_lb);
            coins = itemView.findViewById(R.id.coins_lb);
        }
    }

}
