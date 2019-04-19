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
 * - 'OptionDao' rather than 'WordDao'.
 * - Upgraded to AndroidX
 */

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

    @Insert
    long insert(Option option);

    @Delete
    void delete(Option option);
}
