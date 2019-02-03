package com.lydiaralph.decisiontracker;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends MenuBasedActivity {

    private final TextView pageTitle;

    public MainActivity() {
        this.pageTitle = findViewById(R.id.page_title);
        this.pageTitle.setText("Menu");
        this.pageTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();
    }

}
