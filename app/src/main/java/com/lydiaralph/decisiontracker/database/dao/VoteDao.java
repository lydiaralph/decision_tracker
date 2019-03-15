package com.lydiaralph.decisiontracker.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
