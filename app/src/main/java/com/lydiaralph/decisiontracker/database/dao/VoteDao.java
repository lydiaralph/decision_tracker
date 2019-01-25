package com.lydiaralph.decisiontracker.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.util.List;

@Dao
public interface VoteDao {
    @Query("SELECT * FROM votes")
    LiveData<List<Vote>> getAll();

    @Query("SELECT * FROM votes WHERE decision_id = :decisionId")
    LiveData<List<Vote>> getAllByDecisionId(Integer decisionId);

    @Query("SELECT COUNT(id) FROM votes WHERE decision_id = :decisionId AND option_id = :optionId")
    LiveData<Integer> countVoteByDecisionAndOption(Integer decisionId, Integer optionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vote votes);

    @Delete
    void delete(Vote vote);
}
