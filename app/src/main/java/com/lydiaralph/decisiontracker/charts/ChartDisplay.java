package com.lydiaralph.decisiontracker.charts;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;

public interface ChartDisplay<U> {

    void displayData(U dataSet);

    int[] COLORS = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };
}
