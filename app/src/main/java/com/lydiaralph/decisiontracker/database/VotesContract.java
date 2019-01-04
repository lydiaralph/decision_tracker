package com.lydiaralph.decisiontracker.database;

import android.provider.BaseColumns;

import java.util.Date;

public final class VotesContract {
    private VotesContract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "votes";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_OPTION_ID = "option_id";
        public static final String COLUMN_NAME_VOTE_DATE = "vote_date";
    }
}

