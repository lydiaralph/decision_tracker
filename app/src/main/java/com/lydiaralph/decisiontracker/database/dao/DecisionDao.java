package com.lydiaralph.decisiontracker.database.dao;

import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

@Dao
public interface DecisionDao {
    @Query("SELECT * FROM decision")
    List<Decision> getAll();

    @Insert
    void insert(Decision decisions);

    @Delete
    void delete(Decision decisions);
}
