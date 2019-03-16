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

import java.util.Calendar;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "decisions")
public class Decision {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "decision_text")
    public String decisionText;

    @TypeConverters({Converters.class})
    @ColumnInfo(name = "start_date")
    public Calendar startDate;

    @TypeConverters({Converters.class})
    @ColumnInfo(name = "end_date")
    public Calendar endDate;

    public int getId(){
        return this.id;
    }

    public String getDecisionText(){
        return this.decisionText;
    }

    @Keep
    public Calendar getStartDate(){
        return this.startDate;
    }

    @Keep
    public Calendar getEndDate(){
        return this.endDate;
    }

    /**
     * Start date defaults to today's date. End date defaults to start date + 3 months.
     * @param decisionText
     */
    @Ignore
    public Decision(String decisionText){
        this.decisionText = decisionText;
        this.startDate = Calendar.getInstance();
        this.endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 3);
    }

    public Decision(String decisionText, Calendar startDate, Calendar endDate){
        this.decisionText = decisionText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static class Converters {
        @TypeConverter
        public Calendar fromTimestamp(Long value) {
            Calendar returnDate = Calendar.getInstance();
            returnDate.setTimeInMillis(value);
            return value == null ? null : returnDate;
        }

        @TypeConverter
        public Long dateToTimestamp(Calendar date) {
            if (date == null) {
                return null;
            } else {
                return date.getTimeInMillis();
            }
        }
    }


}
