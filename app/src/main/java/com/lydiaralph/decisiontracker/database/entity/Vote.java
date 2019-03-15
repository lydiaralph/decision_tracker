package com.lydiaralph.decisiontracker.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "votes")
public class Vote {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "decision_id")
    public int decisionId;

    @ColumnInfo(name = "option_id")
    public int optionId;

//    @ColumnInfo(name = "vote_date")
//    public Date voteDate;

    public int getId() {
        return id;
    }

    public int getDecisionId() {
        return decisionId;
    }

//    public Date getVoteDate() {
//        return voteDate;
//    }

    public Vote(int id, int decisionId, int optionId){
        this.id = id;
        this.decisionId = decisionId;
        this.optionId = optionId;
//        this.voteDate = voteDate;
    }
}
