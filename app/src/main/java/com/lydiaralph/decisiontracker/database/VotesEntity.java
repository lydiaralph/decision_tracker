package com.lydiaralph.decisiontracker.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class VotesEntity {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "votes")
    public String votes;

    @ColumnInfo(name = "option_id")
    public int optionId;

//    @ColumnInfo(name = "vote_date")
//    public Date voteDate;
}