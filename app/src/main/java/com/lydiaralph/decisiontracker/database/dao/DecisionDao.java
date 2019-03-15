package com.lydiaralph.decisiontracker.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

@Dao
public interface DecisionDao {
    @Query("SELECT * FROM decisions")
    LiveData<List<Decision>> getAll();

    @Query("SELECT * FROM decisions WHERE id=:decisionId")
    LiveData<Decision> getDecisionById(Integer decisionId);

    @Insert
    void insert(Decision decision);

    @Update
    void update(Decision decision);

    @Delete
    void delete(Decision decision);

    @Query("DELETE FROM decisions")
    void deleteAll();
}
