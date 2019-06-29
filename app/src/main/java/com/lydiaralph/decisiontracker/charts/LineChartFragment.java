package com.lydiaralph.decisiontracker.charts;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;
import com.lydiaralph.decisiontracker.utils.MoodDescriptionWithIntensityComparator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class LineChartFragment extends Fragment implements ChartDisplay<List<MoodDescriptionWithIntensity>> {
    private static final String LOG_NAME = LineChartFragment.class.getSimpleName();
    private LineChart chart;
    private LineData chartData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_chart_view, container, false);
        chart = view.findViewById(R.id.line_chart);
        chart.setData(this.chartData);
        configureChart();
        return view;
    }

    public void displayData(List<MoodDescriptionWithIntensity> data) {
        if (data == null) {
            Log.e(LOG_NAME, "No data supplied for chart");
            return;
        }

        LineData chartData = getChartData(data);
        this.chartData = chartData;
        chart.setData(chartData);

        formatLegend();
        formatXAxis();
        formatYAxis();

        configureChart();
    }

    private void configureChart() {
        chart.getDescription().setText("Moods vs. time");
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setTouchEnabled(true);
        chart.setPinchZoom(true);
        chart.invalidate();
    }

    private void formatYAxis(){
        YAxis y = chart.getAxisLeft();
        y.setTextSize(15f);
    }

    private void formatXAxis() {
        XAxis x = chart.getXAxis();
        x.setValueFormatter(new MyXAxisFormatter());
        x.setTextSize(15f);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private void formatLegend() {
        Legend l = chart.getLegend();
        l.setTextSize(15f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setXEntrySpace(5f);
        l.setStackSpace(5f);
    }

    private class MyXAxisFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            LocalDate dateValue = LocalDate.ofEpochDay((long) value);
            return dateValue.format(DateTimeFormatter.ofPattern("MMM yy"));
        }
    }

    private static void formatDataSet(int index, LineDataSet individualDataSet) {
        individualDataSet.setDrawIcons(false);

        // draw dashed line
        individualDataSet.enableDashedLine(10f, 5f, 0f);

        List<Integer> colors = ChartColors.getColors();

        int colorIndexToUse = (index + colors.size()) % colors.size();
        individualDataSet.setColor(colors.get(colorIndexToUse));
        individualDataSet.setCircleColor(colors.get(colorIndexToUse));

        // line thickness and point size
        individualDataSet.setLineWidth(4f);
        individualDataSet.setCircleRadius(6f);

        // draw points as solid circles
        individualDataSet.setDrawCircleHole(false);

        // customize legend entry
        individualDataSet.setFormLineWidth(4f);
        individualDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        individualDataSet.setFormSize(15.f);

        // text size of values
        individualDataSet.setValueTextSize(15f);

        // draw selection line as dashed
        individualDataSet.enableDashedHighlightLine(10f, 5f, 0f);
    }

    private LineData getChartData(List<MoodDescriptionWithIntensity> inputData) {
        String label = "";
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        ArrayList<Entry> values = new ArrayList<>();

        inputData.sort(new MoodDescriptionWithIntensityComparator());

        for (MoodDescriptionWithIntensity record : inputData) {
            if (record.getDescription() != null && !record.getDescription().equals(label)) {
                if (!label.equals("")) {
                    LineDataSet set = new LineDataSet(values, label);
                    dataSets.add(set);
                    values = new ArrayList<>();
                }
            }
            // Legacy protection: moods with no record because they are new
            if (record.getMoodIntensity() != null) {
                label = record.getDescription();
                Integer y = record.getMoodIntensity();
                long x = record.getVoteDate().toEpochDay();
                values.add(new Entry(x, y));
            }
        }

        // Add final set
        LineDataSet set = new LineDataSet(values, label);
        dataSets.add(set);

        for (int i = 0; i < dataSets.size(); i++) {
            formatDataSet(i, (LineDataSet) dataSets.get(i));
        }

        // create a data object with the data sets
        return new LineData(dataSets);
    }

}