package com.lydiaralph.decisiontracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.database.adapter.DecisionAdapter;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

public class MainActivity extends MenuBasedActivity {

    private static final int NEW_DECISION_REQUEST_CODE = 1;
    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();
        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final DecisionAdapter adapter = new DecisionAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void setConfigureNewDecisionButton() {
        configureNewDecisionButton = findViewById(R.id.button_configure);
        configureNewDecisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfigureNewDecisionActivity.class);
                startActivityForResult(intent, MainActivity.NEW_DECISION_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_DECISION_REQUEST_CODE && resultCode == RESULT_OK) {
            Decision decision = new Decision(data.getStringExtra(ConfigureNewDecisionActivity.INPUT_DECISION_TEXT));
            decisionViewModel.insert(decision);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
