package com.lydiaralph.decisiontracker;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.database.adapter.DecisionAdapter;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.util.List;

public class MainActivity extends MenuBasedActivity {

    private static final int NEW_DECISION_REQUEST_CODE = 1;
    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewResultsButton();
        setConfigureNewDecisionButton();

        final DecisionAdapter adapter = new DecisionAdapter(this);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        final Observer<List<Decision>> decisionObserver = new Observer<List<Decision>>() {
            @Override
            public void onChanged(@Nullable final List<Decision> newDecisions) {
               adapter.setDecisions(newDecisions);
            }
        };

        decisionViewModel.getAllDecisions().observe(this, decisionObserver);
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
