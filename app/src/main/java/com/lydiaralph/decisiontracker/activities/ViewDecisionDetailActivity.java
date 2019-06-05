package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.charts.DayAxisValueFormatter;
import com.lydiaralph.decisiontracker.charts.MyValueFormatter;
import com.lydiaralph.decisiontracker.charts.PieChartDisplay;
import com.lydiaralph.decisiontracker.charts.XYMarkerView;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;
import com.lydiaralph.decisiontracker.database.viewmodel.MoodViewModel;
import com.lydiaralph.decisiontracker.fragments.TerminateDecisionTrackingFragment;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

/**
 * Inspired by https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/PieChartActivity.java
 */

public class ViewDecisionDetailActivity extends MenuBasedActivity implements OnChartValueSelectedListener, TerminateDecisionTrackingFragment.Listener,
        SeekBar.OnSeekBarChangeListener {
    private static final String LOG = ViewDecisionDetailActivity.class.getSimpleName();

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;
    private MoodViewModel moodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_9_view_decision_detail);
        setReturnToMainMenuButton();

        Integer selectedDecisionId = (Integer) getIntent().getExtras().get(ViewDecisionsCategoryActivity.VIEW_DECISION_ID);
        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);
        moodViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoodVif vewModel.class);

        final Observer<DecisionOptions> decisionObserver = getDecisionOptionsObserver();
        final Observer<List<MoodDescriptionWithIntensity>> moodObserver = getMoodObserver();
        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
//        moodViewModel.getAllMoodsByOptionId(option.getOption().getId())
//                .observe(ViewDecisionDetailActivity.this, moodObserver);
    }

    @Override
    public void terminateDecisionTrackingEarly(DialogFragment dialog, int decisionId) {
        // Update decision expiry date to today to stop tracking
        decisionViewModel.updateEndDate(decisionId, LocalDate.now());
        // View results
        Intent intent = getIntent();
        intent.putExtra(ViewDecisionsCategoryActivity.VIEW_DECISION_ID, decisionId);
        finish();
        this.startActivity(intent);
    }

    @Override
    public void keepTrackingDecision(DialogFragment dialog) {
        // Go back to list of decisions
        Intent resultIntent = new Intent(ViewDecisionDetailActivity.this, ViewDecisionsCategoryActivity.class);
        resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
        setResult(Activity.RESULT_CANCELED, resultIntent);
        startActivity(resultIntent);
        finish();
    }

    @NotNull
    private Observer<DecisionOptions> getDecisionOptionsObserver() {
        LinearLayout myRoot = findViewById(R.id.display_options);

        TextView editorialTextView = new TextView(this);

        return new Observer<DecisionOptions>() {
            @Override
            public void onChanged(@Nullable final DecisionOptions decision) {
                if (decision.getDecision().getEndDate().compareTo(LocalDate.now()) > 0) {

                    TerminateDecisionTrackingFragment terminateDecisionTrackingFragment = TerminateDecisionTrackingFragment.newInstance(decision.getDecision().getId());
                    terminateDecisionTrackingFragment.show(getSupportFragmentManager(), "terminateDecision");
                } else {
                    TextView title = findViewById(R.id.page_title);
                    title.setText(getString(R.string.you_decided));

                    TextView datesView = findViewById(R.id.display_dates);
                    datesView.setText(getFormattedDateString(decision));

                    if (!decision.getOptionsList().isEmpty()) {
                        // More efficient with lambda? But bootstrap error
                        boolean hasVotes = false;
                        for (OptionsVotes optionsVotes : decision.getOptionsList()) {
                            if (optionsVotes.countVotes() > 0) {
                                hasVotes = true;
                            }
                        }
                        if (!hasVotes) {
                            editorialTextView.setText(getString(R.string.no_votes_for_this_decision));
                        } else {
                            setPieChart(decision);
                            BarChart barChart = new BarChart(ViewDecisionDetailActivity.this);
                            List<BarEntry> entries = new ArrayList<BarEntry>();
                            makeABarChart();
                        }

//                        for (OptionsVotes option : decision.getOptionsList()) {
//                            moodViewModel.getAllMoodsByOptionId(option.getOption().getId())
//                                    .observe(ViewDecisionDetailActivity.this, getMoodObserver());
//                        }

                        // Get the data, then decide what to do with it later

                        // Graph 1:
                        // Find all moods in this time frame, regardless of decision or option... need to use votes to work out dates

                        // Graph 2:
                        // Either:
                        // Add Mood to votes
                        // OR:
                        // For each option ID, find all the mood types + intensity scores <-- simpler?
                        // == for this option ID, find all the vote IDs, then find all the mood scores for this vote ID

                        // So for both of these, I need to query the mood table for any records that have vote IDs that appear in the list


                        //                        editorialTextView.setText(R.string.you_decided);
                    } else {
                        editorialTextView.setText(R.string.no_options_placeholder);
                    }
                    myRoot.addView(editorialTextView);
                }
            }


        };
    }

    @NotNull
    private Observer<List<MoodDescriptionWithIntensity>> getMoodObserver() {
        return new Observer<List<MoodDescriptionWithIntensity>>() {
            @Override
            public void onChanged(List<MoodDescriptionWithIntensity> moodDescriptionWithIntensities) {

                for (MoodDescriptionWithIntensity moodEntry : moodDescriptionWithIntensities) {
                    String moodTypeDescription = moodEntry.getDescription();
                    Integer intensity = moodEntry.getMoodIntensity();
                }
            }
        };
    }

    private void makeABarChart() {
        BarChart chart = findViewById(R.id.barchart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(1645f, 7));
        NoOfEmp.add(new BarEntry(1578f, 8));
        NoOfEmp.add(new BarEntry(1695f, 9));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);

        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }

    private void setPieChart(@NotNull DecisionOptions decision) {
        PieChart chart = findViewById(R.id.chart1);
        PieChartDisplay pieChartDisplay = new PieChartDisplay(chart, decision);
        pieChartDisplay.displayVotesInPieChart();
        pieChartDisplay.getChart().setOnChartValueSelectedListener(ViewDecisionDetailActivity.this);
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


    private BarChart chart;

    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    public void displayVotesInBarChart(){
        // TODO
    }

    private void configureChart() {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("$");

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        // setting data
        seekBarY.setProgress(50);
        seekBarX.setProgress(12);

    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

//            if (Math.random() * 100 < 25) {
//                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
//            } else {
                values.add(new BarEntry(i, val));
//            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    private List<Integer> getColors(){
        List<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());


        return colors;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    private final RectF onValueSelectedRectF = new RectF();


}

