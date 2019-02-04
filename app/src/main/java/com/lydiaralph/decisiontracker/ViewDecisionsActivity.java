package com.lydiaralph.decisiontracker;

import android.os.Bundle;

public class ViewDecisionsActivity extends MenuBasedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_decisions);
        setReturnToMainMenuButton();
    }
}
