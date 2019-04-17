package com.lydiaralph.decisiontracker;

import android.os.Bundle;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.database.entity.Decision;
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

        final Observer<Decision> decisionObserver = new Observer<Decision>() {
            @Override
            public void onChanged(@Nullable final Decision decision) {
                TextView decisionTextView = findViewById(R.id.display_decision_text);
                decisionTextView.setText(decision.getDecisionText());

                TextView datesView = findViewById(R.id.display_dates);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                datesView.setText(decision.getStartDate().format(formatter) + " - " + decision.getEndDate().format(formatter));
            }
        };

        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
    }
}

