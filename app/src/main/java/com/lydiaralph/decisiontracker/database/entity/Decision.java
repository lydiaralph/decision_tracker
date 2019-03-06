package com.lydiaralph.decisiontracker.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

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
}
