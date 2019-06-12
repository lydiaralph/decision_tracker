package com.lydiaralph.decisiontracker.database.dao;

/*
 * Copyright (C) 2019 Lydia Ralph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Derived from https://github.com/googlecodelabs/android-room-with-a-view
 *
 * Modified:
 * - 'VoteDao' rather than 'WordDao'.
 * - Upgraded to AndroidX
 */

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

    @Query("SELECT * FROM votes WHERE option_id = :optionId")
    LiveData<List<Vote>> getAllByDecisionId(Integer optionId);

    @Query("SELECT COUNT(id) FROM votes WHERE option_id = :optionId")
    LiveData<Integer> countVoteByDecisionAndOption(Integer optionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Vote votes);

    @Delete
    void delete(Vote vote);

    @Query("DELETE FROM votes")
    void deleteAll();
}
