package com.lydiaralph.decisiontracker.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;

public class PieChartFragment extends Fragment implements ChartDisplay<DecisionOptions>, OnChartValueSelectedListener {

    private static final String LOG_NAME = PieChartFragment.class.getSimpleName();
    private PieChart mChart;
    private PieData chartData;
    private String centreText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_chart_view, container, false);
        mChart = view.findViewById(R.id.pie_chart);
        mChart.setData(this.chartData);
        mChart.setCenterText(this.centreText);
        configureChart();
        return view;
    }

    public void displayData(DecisionOptions decisionOptions) {
        if (decisionOptions == null) {
            Log.e(LOG_NAME, "No data supplied for chart");
            return;
        }

        setData(decisionOptions);
        generateCenterText(decisionOptions);
        configureChart();
    }

    private void configureChart() {
        mChart.getDescription().setText("Votes per option");
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateY(1400, Easing.EaseInOutQuad);

        mChart.setCenterTextSize(30f);

        mChart.getLegend().setEnabled(false);
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setWordWrapEnabled(true);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setTextSize(15f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(15f);

        if(chartData != null) {
            chartData.setValueFormatter(new IntegerFormatter());
            chartData.setValueTextSize(15f);
            chartData.setValueTextColor(Color.BLACK);
            mChart.setData(this.chartData);
        }
    }

    private void setData(DecisionOptions decisionOptions) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Collections.sort(decisionOptions.optionsList);
        for (OptionsVotes optionsVotes : decisionOptions.getOptionsList()) {
            if (optionsVotes.countVotes() > 0) {
                entries.add(new PieEntry(optionsVotes.countVotes(), optionsVotes.getOption().getOptionText()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, decisionOptions.getDecision().getDecisionText());
        dataSet.setColors(ChartColors.getColors());
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);

        this.chartData = data;
        this.mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    private void generateCenterText(DecisionOptions decisionOptions) {
        if (decisionOptions == null ||
                decisionOptions.getDecision() == null ||
                decisionOptions.getDecision().getDecisionText() == null) {
            this.centreText = "";
            mChart.setCenterText(centreText);
            return;
        }
        String centerText = "";
        String decisionText = decisionOptions.getDecision().getDecisionText();

        if (decisionText.length() > 40) {
            Integer spaceIndexAfterThirdWay = decisionText.indexOf(" ", Math.round(decisionText.length() / 3));
            Integer spaceIndexAfterSecondThirdWay = decisionText.indexOf(" ", Math.round(decisionText.length() / 3) * 2);

            String first = decisionText.substring(0, spaceIndexAfterThirdWay);
            String second = decisionText.substring(spaceIndexAfterThirdWay, spaceIndexAfterSecondThirdWay);
            String third = decisionText.substring(spaceIndexAfterSecondThirdWay);

            centerText = first + "\n" + second + "\n" + third;
        } else if (decisionText.length() > 20) {
            Integer spaceIndexHalf = decisionText.indexOf(" ", Math.round(decisionText.length() / 2));

            String first = decisionText.substring(0, spaceIndexHalf);
            String second = decisionText.substring(spaceIndexHalf);
            centerText = first + "\n" + second;
        }

        this.centreText = centerText;
        mChart.setCenterText(centerText);
    }

    private class IntegerFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {

            return String.valueOf(Math.round(value));
        }

    }

    @Override
    public void onNothingSelected() {
        Log.i(LOG_NAME, "nothing selected");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }


}