package com.lydiaralph.decisiontracker;

import android.os.Bundle;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.time.format.DateTimeFormatter;

public class ViewDecisionDetailActivity extends MenuBasedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_view_decision_detail);
        setReturnToMainMenuButton();

        // TODO: Replace with actual decision from DB
        Decision decision = new Decision(DateUtilsImpl.getInstance(), "What shall I do next year?");

        TextView decisionTextView = findViewById(R.id.display_decision_text);
        decisionTextView.setText(decision.getDecisionText());

        TextView datesView = findViewById(R.id.display_dates);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        datesView.setText(decision.getStartDate().format(formatter) + " - " + decision.getEndDate().format(formatter));
    }
}

