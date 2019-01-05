package com.lydiaralph.decisiontracker.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

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

    @ColumnInfo(name = "vote_date")
    public Date voteDate;

    public int getId() {
        return id;
    }

    public int getDecisionId() {
        return decisionId;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public Vote(int id, int decisionId, int optionId, Date voteDate){
        this.id = id;
        this.decisionId = decisionId;
        this.optionId = optionId;
        this.voteDate = voteDate;
    }
}
