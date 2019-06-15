package com.lydiaralph.decisiontracker.charts;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public interface ChartDisplay<U> {

    void displayData(U dataSet);



}
