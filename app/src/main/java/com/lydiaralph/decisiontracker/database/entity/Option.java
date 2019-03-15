package com.lydiaralph.decisiontracker.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "options")
public class Option {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "decision_id")
    public int decisionId;

    @ColumnInfo(name = "option_text")
    public String optionText;

    public int getDecisionId() {
        return decisionId;
    }

    public int getId() {
        return id;
    }

    public String getOptionText() {
        return optionText;
    }

    public Option(int id, int decisionId, String optionText){
        this.id = id;
        this.decisionId = decisionId;
        this.optionText = optionText;
    }
}
