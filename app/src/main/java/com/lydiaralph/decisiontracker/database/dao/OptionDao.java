package com.lydiaralph.decisiontracker.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.lydiaralph.decisiontracker.database.entity.Option;

import java.util.List;

@Dao
public interface OptionDao {
    @Query("SELECT * FROM options")
    LiveData<List<Option>> getAll();

    @Query("SELECT * FROM options WHERE decision_id = :decisionId")
    LiveData<List<Option>> getAllByDecisionId(Integer decisionId);
  
    @Insert
    void insertAll(Option... options);

    @Delete
    void delete(Option option);
}
