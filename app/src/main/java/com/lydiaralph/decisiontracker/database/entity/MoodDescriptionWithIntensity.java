package com.lydiaralph.decisiontracker.database.entity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class MoodDescriptionWithIntensity {

    @NonNull
    @ColumnInfo(name = "mood_type_description")
    String description;

    @NonNull
    @ColumnInfo(name = "intensity")
    Integer moodIntensity;

    @NonNull
    @Keep
    public String getDescription() {
        return description;
    }

    @NonNull
    @Keep
    public Integer getMoodIntensity() {
        return moodIntensity;
    }

    public MoodDescriptionWithIntensity(String description, Integer moodIntensity) {
        this.description = description;
        this.moodIntensity = moodIntensity;
    }
}
