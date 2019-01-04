package com.lydiaralph.decisiontracker.database;

import android.provider.BaseColumns;

public final class DecisionsContract {
    private DecisionsContract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "decisions";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DECISION_TEXT = "decision_text";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_END_DATE = "end_date";
    }
}
