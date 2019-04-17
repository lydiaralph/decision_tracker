package com.lydiaralph.decisiontracker.database.entity;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DecisionOptions {

    public DecisionOptions(){
        this.decision = null;
        this.optionsList = new ArrayList<>();
    }

    public DecisionOptions(List<Option> optionsList){
        this.decision = null;
        if(optionsList == null){
            this.optionsList = new ArrayList<>();
        } else {
            this.optionsList = optionsList;
        }
    }

    public DecisionOptions(Decision decision, List<Option> optionsList){
        this.decision = decision;
        if(optionsList == null){
            this.optionsList = new ArrayList<>();
        } else {
            this.optionsList = optionsList;
        }
    }


    @Embedded
    public Decision decision = null;

    @Relation(parentColumn = "id", entityColumn = "decision_id", entity=Option.class)
    public List<Option> optionsList = new ArrayList<>();


    public Decision getDecision() {
        return decision;
    }
    public List<Option> getOptionsList() {
        return optionsList;
    }



}
