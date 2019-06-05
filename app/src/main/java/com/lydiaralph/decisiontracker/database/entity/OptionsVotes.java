package com.lydiaralph.decisiontracker.database.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.room.Embedded;
import androidx.room.Relation;

public class OptionsVotes implements Comparable<OptionsVotes> {

    @Embedded
    public Option option;

    @Relation(parentColumn = "id", entityColumn = "option_id", entity = Vote.class)
    public List<Vote> votesList;

    public Option getOption() {
        return option;
    }

    public List<Integer> getVoteIds (){
        return getVotesList().stream()
                .map(Vote::getId)
                .collect(Collectors.toList());
    }

    public Integer countVotes() {
        if(votesList == null){
            return 0;
        }
        return votesList.size();
    }

    public List<Vote> getVotesList() {
        return votesList;
    }

    public OptionsVotes() {
        this.option = null;
        this.votesList = new ArrayList<>();
    }

    @Override
    public int compareTo(OptionsVotes o) {
        return this.countVotes().compareTo(o.countVotes());
    }

}