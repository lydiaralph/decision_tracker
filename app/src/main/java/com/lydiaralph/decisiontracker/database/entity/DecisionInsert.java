package com.lydiaralph.decisiontracker.database.entity;

import java.util.ArrayList;
import java.util.List;

public class DecisionInsert {

    public Decision decision;
    public List<String> optionTextList;

    public Decision getDecision() {
        return decision;
    }

    public List<String> getOptionTextList() {
        return optionTextList;
    }

    public DecisionInsert(Decision decision, List<String> optionsList){
        this.decision = decision;
        if(optionsList.isEmpty()){
            this.optionTextList = new ArrayList<>();
        } else {
            this.optionTextList = optionsList;
        }
    }

}
