package com.lydiaralph.decisiontracker.database.entity;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DecisionOptions {

    @Embedded
    public Decision decision = null;

    @Relation(parentColumn = "id", entityColumn = "decision_id", entity=Option.class)
    public List<OptionsVotes> optionsList = new ArrayList<>();

    public DecisionOptions(){
        this.decision = null;
        this.optionsList = new ArrayList<>();
    }

    public Decision getDecision() {
        return decision;
    }
    public List<OptionsVotes> getOptionsList() {
        return optionsList;
    }
}
