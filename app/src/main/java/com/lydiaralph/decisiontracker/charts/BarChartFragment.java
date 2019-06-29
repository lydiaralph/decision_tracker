package com.lydiaralph.decisiontracker.charts;

import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * What I want:
 * option descriptions as Axis X labels
 * moods and intensities
 */

public class BarChartFragment extends Fragment implements ChartDisplay<DecisionOptions>, SeekBar.OnSeekBarChangeListener {

    private static final String LOG_NAME = BarChartFragment.class.getSimpleName();
    private BarChart chart;
    private BarData chartData;

    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_view, container, false);
        chart = view.findViewById(R.id.barchart);
        chart.setData(this.chartData);
        return view;
    }

    public void displayData(DecisionOptions decisionOptions) {
        if (decisionOptions == null) {
            Log.e(LOG_NAME, "No data supplied for chart");
            return;
        }

        setData(decisionOptions);
        configureChart();
    }

    private void configureChart() {
        chart.setDrawBarShadow(false);
//        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

//        XAxis xAxis = chart.getXAxis();
//        xAxis.setDrawLabels(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setTextSize(30f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setTextSize(30f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);// this replaces setStartAtZero(true)
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(Math.round(value));
            }
        });

        Legend l = chart.getLegend();
        l.setFormSize(15f);
        l.setTextSize(50f);
        l.setXEntrySpace(34f);

//        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
//        mv.setChartView(chart); // For bounds control
//        chart.setMarker(mv); // Set the marker to the chart

        // setting data
//        seekBarY.setProgress(50);
//        seekBarX.setProgress(12);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
    }

    private void setData(DecisionOptions decisionOptions){
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < decisionOptions.getOptionsList().size(); i++){
            ArrayList<BarEntry> values = new ArrayList<>();
            OptionsVotes optionsVotes = decisionOptions.getOptionsList().get(i);
            values.add(new BarEntry(optionsVotes.getOption().getId(), optionsVotes.getVotesList().size()));
            BarDataSet dataSet = new BarDataSet(values, optionsVotes.getOption().optionText);
            dataSet.setDrawIcons(false);

            List<Integer> colors = ChartColors.getColors();
            int colorIndexToUse = (i + colors.size()) % colors.size();
            dataSet.setColor(colors.get(colorIndexToUse));
            dataSets.add(dataSet);
        }

        // TODO: Really not sure about this clause!
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            ArrayList<BarEntry> values = new ArrayList<>();
            BarDataSet dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            dataSets.add(dataSet);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            BarData data = new BarData(dataSets);
            data.setValueTextSize(15f);
            data.setBarWidth(0.8f);
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf(Math.round(value));
                }
            });

            chart.setData(data);
            chartData = data;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

//        setData(seekBarX.getProgress(), seekBarY.getProgress());
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private final RectF onValueSelectedRectF = new RectF();


}
