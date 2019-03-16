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

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "votes")
public class Vote {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "decision_id")
    public int decisionId;

    @ColumnInfo(name = "option_id")
    public int optionId;

//    @ColumnInfo(name = "vote_date")
//    public Date voteDate;

    public int getId() {
        return id;
    }

    public int getDecisionId() {
        return decisionId;
    }

//    public Date getVoteDate() {
//        return voteDate;
//    }

    public Vote(int id, int decisionId, int optionId){
        this.id = id;
        this.decisionId = decisionId;
        this.optionId = optionId;
//        this.voteDate = voteDate;
    }
}
