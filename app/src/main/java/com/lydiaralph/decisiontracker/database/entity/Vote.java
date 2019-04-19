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
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;
import static java.lang.Math.toIntExact;

@Entity(tableName = "votes",
 indices = {@Index("option_id")},
        foreignKeys = @ForeignKey(entity = Option.class,
        parentColumns = "id",
        childColumns = "option_id",
        onDelete = CASCADE))
public class Vote {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

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
    public int getOptionId() {
        return this.optionId;
    }

    @Keep
    public LocalDate getVoteDate() {
        return voteDate;
    }

    @Ignore
    public Vote(int optionId, LocalDate voteDate){
        this.optionId = optionId;
        this.voteDate = voteDate;
    }

    @Ignore
    public Vote(long optionId, LocalDate voteDate){
        this.optionId = toIntExact(optionId);
        this.voteDate = voteDate;
    }

    public Vote(int id, int optionId, LocalDate voteDate){
        this.id = id;
        this.optionId = optionId;
        this.voteDate = voteDate;
    }

}
