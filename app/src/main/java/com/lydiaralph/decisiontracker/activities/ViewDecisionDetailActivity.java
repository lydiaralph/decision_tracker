package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.charts.PieChartDisplay;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

/**
 * Inspired by https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/PieChartActivity.java
 */

public class ViewDecisionDetailActivity extends MenuBasedActivity implements OnChartValueSelectedListener {
    private static final String LOG = ViewDecisionDetailActivity.class.getSimpleName();

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_9_view_decision_detail);
        setReturnToMainMenuButton();

        Integer selectedDecisionId = (Integer) getIntent().getExtras().get(ViewDecisionsCategoryActivity.VIEW_DECISION_ID);
        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);

        final Observer<DecisionOptions> decisionObserver = getDecisionOptionsObserver();
        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
    }

    @NotNull
    private Observer<DecisionOptions> getDecisionOptionsObserver() {
        LinearLayout myRoot = findViewById(R.id.display_options);

        TextView editorialTextView = new TextView(this);

        return new Observer<DecisionOptions>() {
                @Override
                public void onChanged(@Nullable final DecisionOptions decision) {
                    if(decision.getDecision().getEndDate().compareTo(LocalDate.now()) > 0){
                        Log.i(LOG, getString(R.string.decision_has_not_expired_yet));
                        Toast.makeText(
                                getApplicationContext(),
                                getString(R.string.decision_has_not_expired_yet),
                                Toast.LENGTH_LONG).show();

                        Intent resultIntent = new Intent(ViewDecisionDetailActivity.this, ViewDecisionsCategoryActivity.class);
                        // TODO: set up a mechanism by which users can choose to expire the decision tracking early
                        resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
                        setResult(Activity.RESULT_CANCELED, resultIntent);
                        startActivity(resultIntent);
                        finish();
                    }

                    TextView datesView = findViewById(R.id.display_dates);
                    datesView.setText(getFormattedDateString(decision));

                    if(!decision.getOptionsList().isEmpty()) {
                        // More efficient with lambda? But bootstrap error
                        boolean hasVotes = false;
                        for(OptionsVotes optionsVotes : decision.getOptionsList()){
                            if(optionsVotes.countVotes() > 0){
                                hasVotes = true;
                            }
                        }
                        if (!hasVotes) {
                            editorialTextView.setText(getString(R.string.no_votes_for_this_decision));
                        } else {
                            PieChart chart = findViewById(R.id.chart1);
                            PieChartDisplay pieChartDisplay = new PieChartDisplay(chart, decision);
                            pieChartDisplay.displayVotesInPieChart();
                            pieChartDisplay.getChart().setOnChartValueSelectedListener(ViewDecisionDetailActivity.this);
    //                        editorialTextView.setText(R.string.you_decided);
                        }
                    }
                    else {
                        editorialTextView.setText(R.string.no_options_placeholder);
                    }
                    myRoot.addView(editorialTextView);
                }
            };
    }

    private String getFormattedDateString(@NotNull DecisionOptions decision) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return String.format("%s - %s",
                decision.getDecision().getStartDate().format(formatter),
                decision.getDecision().getEndDate().format(formatter));
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}

