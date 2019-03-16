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
 * Modified: 'Decision' rather than 'Word'.
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "decisions")
public class Decision {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "decision_text")
    public String decisionText;

//    @ColumnInfo(name = "start_date")
//    public Date startDate;
//
//    @ColumnInfo(name = "end_date")
//    public Date endDate;

    public int getId(){
        return this.id;
    }

    public String getDecisionText(){
        return this.decisionText;
    }

//    public Date getStartDate(){
//        return this.getStartDate();
//    }
//
//    public Date getEndDate(){
//        return this.getEndDate();
//    }

    public Decision(String decisionText){
        this.decisionText = decisionText;
//        this.startDate = startDate;
//        this.endDate = endDate;
    }

    public void setDecisionText(String newDecisionText) {
        this.decisionText = newDecisionText;
    }
}
