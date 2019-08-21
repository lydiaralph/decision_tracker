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
 * Modified: 'Decision' rather than 'Word'. Added several fields. Added Calendar conversion methods.
 */

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "decisions")
public class Decision {

    public static final Integer DEFAULT_TRACKER_PERIOD_UNITS = 90;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "decision_text")
    public String decisionText;

    @TypeConverters({ConverterUtils.class})
    @ColumnInfo(name = "start_date")
    @NonNull
    public LocalDate startDate;

    @TypeConverters({ConverterUtils.class})
    @ColumnInfo(name = "end_date")
    @NonNull
    public LocalDate endDate;

    public Integer getId(){
        return this.id;
    }

    public String getDecisionText(){
        return this.decisionText;
    }

    @Keep
    public LocalDate getStartDate(){
        return this.startDate;
    }

    @Keep
    public LocalDate getEndDate(){
        return this.endDate;
    }

    /**
     * Start date defaults to today's date. End date defaults to start date + whatever default is set to.
     * @param decisionText
     */
    @Ignore
    public Decision (DateUtils dateUtils, String decisionText){
        this.decisionText = decisionText;
        this.startDate = dateUtils.getCurrentDate();
        this.endDate = startDate.plus(DEFAULT_TRACKER_PERIOD_UNITS, ChronoUnit.DAYS);
    }

    /**
     * Start date defaults to today's date.
     * @param decisionText
     */
    @Ignore
    public Decision (DateUtils dateUtils, String decisionText, LocalDate endDate){
        this.decisionText = decisionText;
        this.startDate = dateUtils.getCurrentDate();
        this.endDate = endDate;
    }

    // For Room constructor
    public Decision(String decisionText, LocalDate startDate, LocalDate endDate){
        this.decisionText = decisionText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
