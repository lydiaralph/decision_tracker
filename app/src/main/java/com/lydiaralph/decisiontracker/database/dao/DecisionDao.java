package com.lydiaralph.decisiontracker.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

@Dao
public interface DecisionDao {
    @Query("SELECT * FROM decisions")
    LiveData<List<Decision>> getAll();

    @Insert
    void insert(Decision decision);

    @Delete
    void delete(Decision decision);

    @Query("DELETE FROM decisions")
    void deleteAll();
}
