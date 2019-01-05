package com.lydiaralph.decisiontracker.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lydiaralph.decisiontracker.database.entity.Option;

import java.util.List;

@Dao
public interface OptionDao {
    @Query("SELECT * FROM options")
    List<Option> getAll();

    @Query("SELECT * FROM options WHERE decision_id = :decisionId")
    List<Option> getAllByDecisionId(Integer decisionId);
  
    @Insert
    void insertAll(Option... options);

    @Delete
    void delete(Option option);
}
