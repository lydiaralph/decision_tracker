package com.lydiaralph.decisiontracker.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static java.lang.Math.toIntExact;

@Entity(tableName = "mood")
public class Mood {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "vote_id")
    public Integer voteId;

    @NonNull
    @ColumnInfo(name = "mood_id")
    public Integer moodId;

    @NonNull
    @ColumnInfo(name = "intensity")
    public Integer intensity;

    public Integer getId() {
        return id;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public Integer getMoodId() {
        return moodId;
    }

    public Integer getIntensity() {
        return intensity;
    }

    @Ignore
    public Mood(long voteId, long moodId, Integer intensity) {
        this.voteId = toIntExact(voteId);
        this.moodId = toIntExact(moodId);
        this.intensity = intensity;
    }

    public Mood(int id, int voteId, int moodId, Integer intensity) {
        this.id = id;
        this.voteId = voteId;
        this.moodId = moodId;
        this.intensity = intensity;
    }

}