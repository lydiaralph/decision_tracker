package com.lydiaralph.decisiontracker;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.time.format.DateTimeFormatter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ViewDecisionDetailActivity extends MenuBasedActivity {

    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_view_decision_detail);
        setReturnToMainMenuButton();

        Integer selectedDecisionId = (Integer) getIntent().getExtras().get(ViewDecisionsCategoryActivity.VIEW_DECISION_ID);
        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        LinearLayout myRoot = findViewById(R.id.display_options);
        LinearLayout a = new LinearLayout(this);
        a.setOrientation(LinearLayout.VERTICAL);

        TextView optionView = new TextView(this);

        final Observer<DecisionOptions> decisionObserver = new Observer<DecisionOptions>() {
            @Override
            public void onChanged(@Nullable final DecisionOptions decision) {
                TextView decisionTextView = findViewById(R.id.display_decision_text);
                decisionTextView.setText(decision.getDecision().getDecisionText());

                TextView datesView = findViewById(R.id.display_dates);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                datesView.setText(String.format("%s - %s",
                        decision.getDecision().getStartDate().format(formatter),
                        decision.getDecision().getEndDate().format(formatter)));

                if(!decision.getOptionsList().isEmpty()){
                    setDecisionOptionsView(a, decision, optionView);
                }
                myRoot.addView(a);
            }
        };

        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
    }

    private void setDecisionOptionsView(LinearLayout a, final DecisionOptions decision, final TextView optionView) {
        for(OptionsVotes option : decision.getOptionsList()){
            TextView optionTextView = new TextView(this);
            optionTextView.setText(option.getOption().getOptionText());
            a.addView(optionTextView);

            if(!option.getVotesList().isEmpty()){
                setVotesView(a, option);
            }
        }
    }

    private void setVotesView(LinearLayout a, OptionsVotes option) {
        for(Vote vote : option.getVotesList()){
            TextView voteView = new TextView(this);
            voteView.setText("Voted for this option on " + vote.getVoteDate());
            a.addView(voteView);
        }
    }
}

