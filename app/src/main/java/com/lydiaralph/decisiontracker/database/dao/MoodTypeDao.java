package com.lydiaralph.decisiontracker.database.dao;

import com.lydiaralph.decisiontracker.database.entity.MoodType;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MoodTypeDao {
    @Query("SELECT * FROM mood_type")
    LiveData<List<MoodType>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MoodType moodType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MoodType> moodTypes);

    @Delete
    void delete(MoodType moodType);

    @Query("DELETE FROM mood_type")
    void deleteAll();
}
