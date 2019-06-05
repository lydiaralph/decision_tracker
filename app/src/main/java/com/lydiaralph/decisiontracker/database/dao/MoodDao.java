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

//    @Query("SELECT mood_type.mood_type_description, mood.intensity FROM mood_type " +
//            " LEFT JOIN mood ON mood.mood_id = mood_type.id WHERE mood.vote_id IN (:voteList)")
//    LiveData<Map<String, Integer>> getAllMoodsByVoteIds(List<Integer> voteList);

    @Query("SELECT mood_type.mood_type_description, mood.intensity FROM mood_type " +
            " LEFT JOIN mood ON mood.mood_id = mood_type.id WHERE mood.vote_id IN" +
            " (SELECT vote_id FROM votes WHERE option_id = :optionId)")
    LiveData<List<MoodDescriptionWithIntensity>> getAllMoodsByOptionId(Integer optionId);

    @Delete
    void delete(Mood mood);

    @Query("DELETE FROM mood")
    void deleteAll();
}
