package com.lydiaralph.decisiontracker.database.entity;

import java.util.Date;

@Entity(tableName = "decisions")
public class Decision {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "decision_text")
    public String decisionText;

    @ColumnInfo(name = "start_date")
    public Date startDate;

    @ColumnInfo(name = "end_date")
    public Date endDate;

    public int getId(){
        return this.id;
    }

    public String getDecisionText(){
        return this.decisionText;
    }

    public Date getStartDate(){
        return this.getStartDate();
    }

    public Date getEndDate(){
        return this.getEndDate();
    }

    public Decision(int id, String decisionText, Date startDate, Date endDate){
        this.id = id;
        this.decisionText = decisionText;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
