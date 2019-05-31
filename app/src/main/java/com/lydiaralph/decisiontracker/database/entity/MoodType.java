package com.lydiaralph.decisiontracker.database.entity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mood_type")
public class MoodType {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "mood_type_description")
    public String description;

    @Keep
    public int getId() {
        return this.id;
    }

    @Keep
    public String getDescription() {
        return this.description;
    }

    @Ignore
    public MoodType(String description){
        this.description = description;
    }

    public MoodType(int id, String description){
        this.id = id;
        this.description = description;
    }

}
