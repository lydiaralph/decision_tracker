package com.lydiaralph.decisiontracker.database;

import java.util.Date;

public class VotesEntity {

    @Entity
    public class VotesEntity {
        @PrimaryKey
        public int uid;

        @ColumnInfo(name = "votes")
        public String votes;

        @ColumnInfo(name = "option_id")
        public int optionId;

        @ColumnInfo(name = "vote_date")
        public Date voteDate;
    }
}
