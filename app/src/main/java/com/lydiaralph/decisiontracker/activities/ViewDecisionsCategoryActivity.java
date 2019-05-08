package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.dagger.DecisionViewModelFactory;
import com.lydiaralph.decisiontracker.database.adapter.DecisionAdapter;
import com.lydiaralph.decisiontracker.database.adapter.VoteDecisionAdapter;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class ViewDecisionsCategoryActivity extends MenuBasedActivity {
    private static final String LOG = ViewDecisionsCategoryActivity.class.getSimpleName();

    public static final String VOTE = "VOTE";
    public static final String VIEW = "VIEW";

    public static final String VIEW_DECISION_ID = "ViewDecisionId";

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");

        setContentView(R.layout.activity_8_view_decisions_category);
        setReturnToMainMenuButton();

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);

        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.getAction() != null) {
            if (callingIntent.getAction().equals(ViewDecisionsCategoryActivity.VIEW)) {
                setOnClickToViewDetailedResult(recyclerView);
            } else if (callingIntent.getAction().equals(VOTE)) {
                setOnClickToVote(recyclerView);
            } else {
                Log.e(LOG, "Unrecognised calling intent: " + callingIntent.getAction());
            }
        }
    }

    private void setOnClickToVote(RecyclerView recyclerView) {
        final VoteDecisionAdapter voteDecisionAdapter = new VoteDecisionAdapter(this);
        recyclerView.setAdapter(voteDecisionAdapter);

        final Observer<List<Decision>> decisionObserver = new Observer<List<Decision>>() {
            @Override
            public void onChanged(@Nullable final List<Decision> newDecisions) {
                voteDecisionAdapter.setDecisions(newDecisions);
            }
        };
        decisionViewModel.getAllDecisions().observe(this, decisionObserver);
    }

    private void setOnClickToViewDetailedResult(RecyclerView recyclerView) {
        final DecisionAdapter decisionAdapter = new DecisionAdapter(this);
        recyclerView.setAdapter(decisionAdapter);

        final Observer<List<Decision>> decisionObserver = new Observer<List<Decision>>() {
            @Override
            public void onChanged(@Nullable final List<Decision> newDecisions) {
                decisionAdapter.setDecisions(newDecisions);
            }
        };
        decisionViewModel.getAllDecisions().observe(this, decisionObserver);
    }
}

