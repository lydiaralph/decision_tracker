package com.lydiaralph.decisiontracker.database.entity;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class OptionsVotes {

    @Embedded
    public Option option = null;

    @Relation(parentColumn = "id", entityColumn = "option_id", entity = Vote.class)
    public List<Vote> votesList = new ArrayList<>();


    public Option getOption() {
        return option;
    }

    public List<Vote> getVotesList() {
        return votesList;
    }

    public OptionsVotes() {
        this.option = null;
        this.votesList = new ArrayList<>();
    }

}