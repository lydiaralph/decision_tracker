package com.lydiaralph.decisiontracker.activities;

import android.os.Bundle;
import android.util.Log;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

public class MainActivity extends MenuBasedActivity {

    private static final String LOG = MainActivity.class.getSimpleName();

    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_1_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();
        setVoteOnDecisionButton();
    }
}
