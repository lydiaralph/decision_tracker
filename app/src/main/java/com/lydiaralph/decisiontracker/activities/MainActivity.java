package com.lydiaralph.decisiontracker.activities;

import android.os.Bundle;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

public class MainActivity extends MenuBasedActivity {

    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();
    }
}
