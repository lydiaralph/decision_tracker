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
 * - 'DecisionDao' rather than 'WordDao'.
 * - Added update query
 * - Upgraded to AndroidX
 */

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.lydiaralph.decisiontracker.database.entity.ConverterUtils;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;

import java.time.LocalDate;
import java.util.List;


@Dao
public interface DecisionDao {
    @Query("SELECT * FROM decisions")
    LiveData<List<Decision>> getAll();

    @Transaction
    @Query("SELECT * FROM decisions WHERE id=:decisionId")
    LiveData<DecisionOptions> getDecisionById(Integer decisionId);

    @Insert
    long insert(Decision decision);

    @Query("UPDATE decisions SET end_date=:endDate WHERE id=:decisionId")
    void updateEndDate(int decisionId, @TypeConverters({ConverterUtils.class}) LocalDate endDate);

    @Delete
    void delete(Decision decision);

    @Query("DELETE FROM decisions")
    void deleteAll();
}
