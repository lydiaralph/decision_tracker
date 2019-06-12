package com.lydiaralph.decisiontracker.database.entity;

import java.time.LocalDate;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

public class MoodDescriptionWithIntensity {

    @NonNull
    @ColumnInfo(name="option_id")
    Integer optionId;

    @NonNull
    @TypeConverters({ConverterUtils.class})
    @ColumnInfo(name = "vote_date")
    public LocalDate voteDate;

    @NonNull
    @ColumnInfo(name = "mood_type_description")
    String description;

    @NonNull
    @ColumnInfo(name = "intensity")
    Integer moodIntensity;

    @Keep
    @NonNull
    public Integer getOptionId() {
        return optionId;
    }

    @Keep
    @NonNull
    public LocalDate getVoteDate() {
        return voteDate;
    }

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

    public MoodDescriptionWithIntensity(Integer optionId, LocalDate voteDate, String description, Integer moodIntensity) {
        this.optionId = optionId;
        this.voteDate = voteDate;
        this.description = description;
        this.moodIntensity = moodIntensity;
    }
}
