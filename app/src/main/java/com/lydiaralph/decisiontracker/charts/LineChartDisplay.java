package com.lydiaralph.decisiontracker.charts;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.util.Log;

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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lydiaralph.decisiontracker.activities.MenuBasedActivity;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;
import com.lydiaralph.decisiontracker.utils.MoodDescriptionWithIntensityComparator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineChartDisplay implements ChartDisplay<LineData> {

    private static final String LOG = MenuBasedActivity.class.getSimpleName();

    private LineChart chart;

    public LineChartDisplay(LineChart chart) {
        this.chart = chart;
    }

    public void displayData(LineData data) {
        if (this.chart.getClass() != LineChart.class) {
            String errorMessage = "Trying to use incorrect chart type for these datasets";
            Log.e(LOG, errorMessage);
            throw new RuntimeException();
        }

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        chart.setAutoScaleMinMaxEnabled(true);
        XAxis x = chart.getXAxis();
        x.setValueFormatter(new MyXAxisFormatter());
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setData(data);
        chart.invalidate();
    }

    public class MyXAxisFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis){
            LocalDate dateValue = LocalDate.ofEpochDay((long) value);
            return dateValue.format(DateTimeFormatter.ofPattern("MMM yy"));
        }

    }

    private static void formatDataSet(int index, LineDataSet individualDataSet){
        individualDataSet.setDrawIcons(false);

        // draw dashed line
        individualDataSet.enableDashedLine(10f, 5f, 0f);

        int colorIndexToUse = (index + ChartDisplay.COLORS.length) % ChartDisplay.COLORS.length;
        individualDataSet.setColor(ChartDisplay.COLORS[colorIndexToUse]);
        individualDataSet.setCircleColor(ChartDisplay.COLORS[colorIndexToUse]);

        // line thickness and point size
        individualDataSet.setLineWidth(1f);
        individualDataSet.setCircleRadius(3f);

        // draw points as solid circles
        individualDataSet.setDrawCircleHole(false);

        // customize legend entry
        individualDataSet.setFormLineWidth(1f);
        individualDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        individualDataSet.setFormSize(15.f);

        // text size of values
        individualDataSet.setValueTextSize(9f);

        // draw selection line as dashed
        individualDataSet.enableDashedHighlightLine(10f, 5f, 0f);
    }

    public static LineData setData(List<MoodDescriptionWithIntensity> inputData) {
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

        for(int i = 0; i < dataSets.size(); i++){
            formatDataSet(i, (LineDataSet) dataSets.get(i));
        }

        // create a data object with the data sets
        return new LineData(dataSets);
    }

}
