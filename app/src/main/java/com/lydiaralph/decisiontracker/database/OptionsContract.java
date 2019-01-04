package com.lydiaralph.decisiontracker.database;

import android.provider.BaseColumns;

public final class OptionsContract {
    private OptionsContract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "options";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DECISION_ID = "decision_id";
        public static final String COLUMN_NAME_OPTION_TEXT = "option_text";
    }
}
