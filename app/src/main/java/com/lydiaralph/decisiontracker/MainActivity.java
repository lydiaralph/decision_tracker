package com.lydiaralph.decisiontracker;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends MenuBasedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();
    }

}
