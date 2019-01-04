package com.lydiaralph.decisiontracker.database.dao;

import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.util.List;

@Dao
public interface VoteDao {
    @Query("SELECT * FROM vote")
    List<Vote> getAll();

    @Query("SELECT * FROM vote WHERE decision_id = :decisionId")
    List<Vote> getAllByDecisionId(Integer decisionId);

    @Query("SELECT COUNT(id) FROM vote WHERE decision_id = :decisionId AND WHERE option_id = :optionId)
    Integer countVoteByDecisionAndOption(Integer decisionId, Integer optionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vote votes);

    @Delete
    void delete(Vote vote);
}
