package com.lydiaralph.decisiontracker.database.dao;

import com.lydiaralph.decisiontracker.database.entity.Mood;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;
import com.lydiaralph.decisiontracker.database.entity.MoodType;

import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mood mood);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Mood> moods);

    @Query("SELECT mood_type.mood_type_description, mood.intensity, " +
            " votes.vote_date, options.id AS option_id FROM options " +
            " LEFT JOIN votes     ON votes.option_id = options.id " +
            " LEFT JOIN mood      ON mood.vote_id    = votes.id " +
            " LEFT JOIN mood_type ON mood.mood_id    = mood_type.id " +
            " WHERE options.id IN " +
            " (SELECT id FROM options WHERE decision_id = :decisionId) ")
    LiveData<List<MoodDescriptionWithIntensity>> getAllMoodsByDecisionId(Integer decisionId);

    @Delete
    void delete(Mood mood);

    @Query("DELETE FROM mood")
    void deleteAll();
}
