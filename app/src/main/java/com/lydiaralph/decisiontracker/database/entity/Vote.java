package com.lydiaralph.decisiontracker.database.entity;

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
 * Modified: 'Vote' rather than 'Word'.
 */

import java.time.LocalDate;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

@Entity(tableName = "votes")
public class Vote {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "decision_id")
    public int decisionId;

    @NonNull
    @ColumnInfo(name = "option_id")
    public int optionId;

    @NonNull
    @TypeConverters({ConverterUtils.class})
    @ColumnInfo(name = "vote_date")
    public LocalDate voteDate;

    @Keep
    public int getId() {
        return this.id;
    }

    @Keep
    public int getDecisionId() {
        return this.decisionId;
    }

    @Keep
    public int getOptionId() {
        return this.optionId;
    }

    @Keep
    public LocalDate getVoteDate() {
        return voteDate;
    }

    @Ignore
    public Vote(int decisionId, int optionId, LocalDate voteDate){
        this.decisionId = decisionId;
        this.optionId = optionId;
        this.voteDate = voteDate;
    }

    public Vote(int id, int decisionId, int optionId, LocalDate voteDate){
        this.id = id;
        this.decisionId = decisionId;
        this.optionId = optionId;
        this.voteDate = voteDate;
    }

}
