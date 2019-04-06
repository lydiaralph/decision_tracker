package com.lydiaralph.decisiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.database.adapter.DecisionAdapter;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDecisionsActivity extends MenuBasedActivity {

    private DecisionViewModel decisionViewModel;
    public static final int NEW_DECISION_REQUEST_CODE = 1;

    protected View saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_view_decisions);
        setReturnToMainMenuButton();

        final DecisionAdapter adapter = new DecisionAdapter(this);
        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        final Observer<List<Decision>> decisionObserver = new Observer<List<Decision>>() {
            @Override
            public void onChanged(@Nullable final List<Decision> newDecisions) {
                adapter.setDecisions(newDecisions);
            }
        };

        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.getAction() != null) {
            if (callingIntent.getAction().equals(ConfigureNewDecisionActivity.PERSIST)) {
                if(callingIntent.getExtras() != null) {
                    String inputText = (String) callingIntent.getExtras().get(ConfigureNewDecisionActivity.INPUT_DECISION_TEXT);
                    if (inputText != null) {
                        Decision decision = new Decision(inputText);
                        decisionViewModel.insert(decision);
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        decisionViewModel.getAllDecisions().observe(this, decisionObserver);
    }
}
