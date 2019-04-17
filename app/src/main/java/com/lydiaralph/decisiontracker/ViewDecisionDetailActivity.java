package com.lydiaralph.decisiontracker;

import android.os.Bundle;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
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

        final Observer<DecisionOptions> decisionObserver = new Observer<DecisionOptions>() {
            @Override
            public void onChanged(@Nullable final DecisionOptions decision) {
                TextView decisionTextView = findViewById(R.id.display_decision_text);
                decisionTextView.setText(decision.getDecision().getDecisionText());

                TextView datesView = findViewById(R.id.display_dates);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                datesView.setText(decision.getDecision().getStartDate().format(formatter)
                        + " - " + decision.getDecision().getEndDate().format(formatter));

                // TODO: Change to 'for each option'
                TextView optionView = findViewById(R.id.display_option_text);
                if(!decision.getOptionsList().isEmpty()){
                    optionView.setText(decision.getOptionsList().get(0).optionText);
                }
            }
        };

        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
    }
}

