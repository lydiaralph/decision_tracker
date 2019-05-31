package com.lydiaralph.decisiontracker.database.dao;

import com.lydiaralph.decisiontracker.database.entity.Mood;
import com.lydiaralph.decisiontracker.database.entity.MoodType;

import java.util.List;

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

    @Delete
    void delete(Mood mood);

    @Query("DELETE FROM mood")
    void deleteAll();
}
