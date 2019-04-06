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

import java.util.Calendar;

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
    public static final int DEFAULT_TRACKER_PERIOD_TYPE = Calendar.DATE;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "decision_text")
    public String decisionText;

    @TypeConverters({ConverterUtils.class})
    @ColumnInfo(name = "start_date")
    @NonNull
    public Calendar startDate;

    @TypeConverters({ConverterUtils.class})
    @ColumnInfo(name = "end_date")
    @NonNull
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

    @Ignore
    public Decision(String decisionText, String trackerPeriodType, Integer trackerPeriodUnits){
        int trackerPeriodCalendarType = TrackerPeriodType.convertToCalendar(trackerPeriodType);

        if(trackerPeriodUnits.equals(0)){
            trackerPeriodUnits = DEFAULT_TRACKER_PERIOD_UNITS;
            trackerPeriodCalendarType = DEFAULT_TRACKER_PERIOD_TYPE;
        }

        this.decisionText = decisionText;
        this.startDate = Calendar.getInstance();
        this.endDate = Calendar.getInstance();
        endDate.add(trackerPeriodCalendarType, trackerPeriodUnits);
    }

    public Decision(String decisionText, Calendar startDate, Calendar endDate){
        this.decisionText = decisionText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public enum TrackerPeriodType{
        DAYS,
        WEEKS,
        MONTHS;

        public static int convertToCalendar(String trackerPeriodType){
            if(trackerPeriodType == null){
                return Calendar.DATE;
            }
            TrackerPeriodType type = valueOf(trackerPeriodType.toUpperCase());
            switch(type) {
                case DAYS:
                    return Calendar.DATE;
                case WEEKS:
                    return Calendar.WEEK_OF_YEAR;
                case MONTHS:
                    return Calendar.MONTH;
            }
            return Calendar.DATE;
        }
    }

}
