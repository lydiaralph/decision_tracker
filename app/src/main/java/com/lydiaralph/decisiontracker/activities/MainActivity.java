package com.lydiaralph.decisiontracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.fragments.DeleteDataFragment;
import com.lydiaralph.decisiontracker.fragments.TerminateDecisionTrackingFragment;

public class MainActivity extends MenuBasedActivity {

    private static final String LOG = MainActivity.class.getSimpleName();
    private View deleteDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_1_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();
        setVoteOnDecisionButton();
        setDeleteButton();
    }

    private void setDeleteButton() {
        deleteDataButton = findViewById(R.id.button_delete);
        deleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
