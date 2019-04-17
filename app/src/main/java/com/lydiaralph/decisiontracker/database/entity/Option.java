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
 * Modified: 'Option' rather than 'Word'.
 */

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "options",
        indices = {@Index("decision_id")},
        foreignKeys = @ForeignKey(entity = Decision.class,
                parentColumns = "id",
                childColumns = "decision_id",
                onDelete = CASCADE))
public class Option {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "decision_id")
    public Integer decisionId;

    @NonNull
    @ColumnInfo(name = "option_text")
    public String optionText;

    @Keep
    public Integer getDecisionId() {
        return decisionId;
    }

    public Integer getId() {
        return id;
    }

    @Keep
    public String getOptionText() {
        return optionText;
    }

    @Ignore
    public Option(int decisionId, String optionText){
        this.decisionId = decisionId;
        this.optionText = optionText;
    }

    public Option(int id, int decisionId, String optionText){
        this.id = id;
        this.decisionId = decisionId;
        this.optionText = optionText;
    }
}
