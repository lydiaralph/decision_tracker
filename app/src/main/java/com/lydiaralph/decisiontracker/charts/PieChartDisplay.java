package com.lydiaralph.decisiontracker.charts;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieChartDisplay {

    private PieChart chart;
    private DecisionOptions decisionOptions;

    public PieChartDisplay(PieChart pieChart, DecisionOptions decisionOptions) {
        this.chart = pieChart;
        this.decisionOptions = decisionOptions;
    }

    public PieChart getChart() {
        return chart;
    }

    public void displayVotesInPieChart() {
        configureChart();
        setData();
    }

    private void configureChart() {
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterText(generateCenterText());
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
//        chart.setOnChartValueSelectedListener(this);
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

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Collections.sort(decisionOptions.optionsList);
        for (OptionsVotes optionsVotes : decisionOptions.getOptionsList()) {
            if (optionsVotes.countVotes() > 0) {
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

    private String generateCenterText() {
        String decisionText = decisionOptions.getDecision().getDecisionText();

        if (decisionText.length() > 40) {
            Integer spaceIndexAfterThirdWay = decisionText.indexOf(" ", Math.round(decisionText.length() / 3));
            Integer spaceIndexAfterSecondThirdWay = decisionText.indexOf(" ", Math.round(decisionText.length() / 3) * 2);

            String first = decisionText.substring(0, spaceIndexAfterThirdWay);
            String second = decisionText.substring(spaceIndexAfterThirdWay, spaceIndexAfterSecondThirdWay);
            String third = decisionText.substring(spaceIndexAfterSecondThirdWay);

            return first + "\n" + second + "\n" + third;
        } else if (decisionText.length() > 20) {
            Integer spaceIndexHalf = decisionText.indexOf(" ", Math.round(decisionText.length() / 2));

            String first = decisionText.substring(0, spaceIndexHalf);
            String second = decisionText.substring(spaceIndexHalf);
            return first + "\n" + second;
        }
        return decisionText;
    }

    private class IntegerFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {

            return String.valueOf(Math.round(value));
        }
    }
}