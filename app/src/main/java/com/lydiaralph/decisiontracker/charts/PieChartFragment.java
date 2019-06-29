package com.lydiaralph.decisiontracker.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.github.mikephil.charting.utils.MPPointF;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;

public class PieChartFragment extends Fragment implements ChartDisplay<DecisionOptions>, OnChartValueSelectedListener {

    private static final String LOG_NAME = PieChartFragment.class.getSimpleName();
    private PieChart chart;
    private PieData chartData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_chart_view, container, false);
        chart = view.findViewById(R.id.pie_chart);
        chart.setData(this.chartData);
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
        chart.getDescription().setText("Votes per option");
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
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
        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setTextSize(15f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(15f);

        if(chartData != null) {
            chartData.setValueFormatter(new IntegerFormatter());
            chartData.setValueTextSize(15f);
            chartData.setValueTextColor(Color.BLACK);
            chart.setData(this.chartData);
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
        this.chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }

    private void generateCenterText(DecisionOptions decisionOptions) {
        if (decisionOptions == null ||
                decisionOptions.getDecision() == null ||
                decisionOptions.getDecision().getDecisionText() == null) {
            chart.setCenterText("");
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

        chart.setCenterText(centerText);
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