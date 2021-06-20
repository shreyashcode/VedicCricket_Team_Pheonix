package com.example.vediccricket;

import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder> {

    protected ArrayList<PracticeModel> Topics;
    protected OnClickListenerInterface onClickListenerInterface;
    public PracticeAdapter(ArrayList<PracticeModel> Topics, OnClickListenerInterface onClickListenerInterface) {
        this.Topics = Topics;
        this.onClickListenerInterface = onClickListenerInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_item, parent, false);
        return new ViewHolder(view, onClickListenerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PracticeModel practiceModel = Topics.get(position);
        //User.topicsLearned.contains(practiceModel.getTopicName())
        if(practiceModel.isLearned == true){
            Log.d("USER", practiceModel.getTopicName()+"HERE");
            holder.backCard.setCardBackgroundColor(Color.parseColor("#ABE849"));
        }else{
            holder.backCard.setCardBackgroundColor(Color.parseColor("#F3F7F3"));
        }
        Log.d("Practice", "HERE"+practiceModel.getTopicName()+" "+practiceModel.getLevel());
        holder.lvl.setText(String.valueOf(practiceModel.getLevel()));
        holder.name.setText(practiceModel.getTopicName());
        holder.reward.setText(String.valueOf(practiceModel.getRewardCoins()));
    }

    @Override
    public int getItemCount() {
        return Topics.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView lvl;
        public TextView name;
        public TextView reward;
        public CardView backCard;
        public OnClickListenerInterface onClickListenerInterface;
        public ViewHolder(@NonNull View itemView, OnClickListenerInterface onClickListenerInterface) {
            super(itemView);
            backCard = itemView.findViewById(R.id.background);
            lvl = itemView.findViewById(R.id.lvl);
            name = itemView.findViewById(R.id.topic_name);
            reward = itemView.findViewById(R.id.reward);
            this.onClickListenerInterface = onClickListenerInterface;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerInterface.onClick(getAdapterPosition());
        }
    }

    public interface OnClickListenerInterface {
        void onClick(int position);
    }
}
