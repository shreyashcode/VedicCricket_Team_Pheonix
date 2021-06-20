package com.example.vediccricket;

import androidx.annotation.NonNull;

public class PracticeModel {
    public String TopicName;
    public int level;
    public int rewardCoins;
    public boolean isLearned;

    public PracticeModel(String topicName, int level, int rewardCoins, boolean isLearned) {
        TopicName = topicName;
        this.level = level;
        this.rewardCoins = rewardCoins;
        this.isLearned = isLearned;
    }

    @NonNull
    @Override
    public String toString() {
        return TopicName + " " + rewardCoins;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRewardCoins() {
        return rewardCoins;
    }

    public void setRewardCoins(int rewardCoins) {
        this.rewardCoins = rewardCoins;
    }

    public boolean isLearned() {
        return isLearned;
    }

    public void setLearned(boolean learned) {
        isLearned = learned;
    }
}