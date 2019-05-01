package com.lydiaralph.decisiontracker.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * Inspired by https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/PieChartActivity.java
 */

public class ViewDecisionDetailActivity extends MenuBasedActivity implements OnChartValueSelectedListener {
    private static final String LOG = ViewDecisionDetailActivity.class.getSimpleName();

    private DecisionViewModel decisionViewModel;

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_view_decision_detail);
        setReturnToMainMenuButton();

        Integer selectedDecisionId = (Integer) getIntent().getExtras().get(ViewDecisionsCategoryActivity.VIEW_DECISION_ID);
        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        LinearLayout myRoot = findViewById(R.id.display_options);
        LinearLayout optionsHolderView = new LinearLayout(this);
        optionsHolderView.setOrientation(LinearLayout.VERTICAL);

        TextView editorialTextView = new TextView(this);

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
                        displayVotesInPieChart(decision);
//                    setDecisionOptionsView(optionsHolderView, decision);
                        editorialTextView.setText(R.string.you_decided);
                    }
                }
                else {
                    editorialTextView.setText(R.string.no_options_placeholder);
                }
                myRoot.addView(optionsHolderView);
            }
        };
        myRoot.addView(editorialTextView);

        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
    }

    // TODO: Can swap this out with pie chart or other graphic
    private void setDecisionOptionsView(LinearLayout optionsHolderView,
                                        final DecisionOptions decision) {
        for(OptionsVotes option : decision.getOptionsList()){
            TextView optionTextView = new TextView(this);
            optionTextView.setText(option.getOption().getOptionText());
            optionsHolderView.addView(optionTextView);

            TextView voteCountView = new TextView(this);
            Integer voteCount = option.countVotes();
            voteCountView.setText(voteCount.toString());
            optionsHolderView.addView(voteCountView);

// Shows dates on which options were voted for. Perhaps move to separate OptionDetailedActivity?
//            if(!option.getVotesList().isEmpty()){
//                setVotesView(optionsHolderView, option);
//            }
        }
    }

    private void setVotesView(LinearLayout a, OptionsVotes option) {
        for(Vote vote : option.getVotesList()){
            TextView voteView = new TextView(this);
            voteView.setText("Voted for this option on " + vote.getVoteDate());
            a.addView(voteView);
        }
    }

    private void displayVotesInPieChart(DecisionOptions decisionOptions){
        configureChart(decisionOptions);
        setData(decisionOptions);
    }

    private void configureChart(DecisionOptions decisionOptions) {
        chart = findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterText(generateCenterText(decisionOptions));
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(this);
        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(15f);
    }

    private void setData(DecisionOptions decisionOptions) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Collections.sort(decisionOptions.optionsList);
        for(OptionsVotes optionsVotes : decisionOptions.getOptionsList()){
            if(optionsVotes.countVotes() > 0) {
                entries.add(new PieEntry(optionsVotes.countVotes(), optionsVotes.getOption().getOptionText()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, decisionOptions.getDecision().getDecisionText());

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        dataSet.setColors(getColors());

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new IntegerFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    private List<Integer> getColors() {
        List<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }

    private String generateCenterText(DecisionOptions decisionOptions) {
        String decisionText = decisionOptions.getDecision().getDecisionText();
        Integer spaceIndexAfterThirdWay = decisionText.indexOf(" ", Math.round(decisionText.length() / 3));
        Integer spaceIndexAfterSecondThirdWay = decisionText.indexOf(" ", Math.round(decisionText.length() / 3) * 2);

        String first = decisionText.substring(0, spaceIndexAfterThirdWay);
        String second = decisionText.substring(spaceIndexAfterThirdWay, spaceIndexAfterSecondThirdWay);
        String third = decisionText.substring(spaceIndexAfterSecondThirdWay);
        String textWithBreak = first + "\n" + second + "\n" + third;

        return textWithBreak;
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

    public class IntegerFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {

            return String.valueOf(Math.round(value));
        }
    }

}

