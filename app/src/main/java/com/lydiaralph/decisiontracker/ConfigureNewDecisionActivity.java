package com.lydiaralph.decisiontracker;

import android.os.Bundle;

public class ConfigureNewDecisionActivity extends MenuBasedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_new_decision);
        setReturnToMainMenuButton();
    }

}
