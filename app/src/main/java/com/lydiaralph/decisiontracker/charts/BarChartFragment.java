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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * What I want:
 * option descriptions as Axis X labels
 * moods and intensities
 */

public class BarChartFragment extends Fragment implements ChartDisplay<DecisionOptions>, SeekBar.OnSeekBarChangeListener {

    private static final String LOG_NAME = BarChartFragment.class.getSimpleName();
    private BarChart mChart;
    private BarData chartData;

    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_view, container, false);
        mChart = view.findViewById(R.id.barchart);
        mChart.setData(this.chartData);
        configureChart();
        return view;
    }

    public void displayData(DecisionOptions decisionOptions) {
        if (decisionOptions == null) {
            Log.e(LOG_NAME, "No data supplied for mChart");
            return;
        }

        setData(decisionOptions);
        configureChart();
    }

    private void configureChart() {
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setText("");
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        YAxis leftAxis = mChart.getAxisLeft();
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

        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawLabels(false);

        Legend l = mChart.getLegend();
        l.setFormSize(15f);
        l.setTextSize(30f);
        l.setXEntrySpace(34f);
        l.setWordWrapEnabled(true);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
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
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            ArrayList<BarEntry> values = new ArrayList<>();
            BarDataSet dataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            dataSets.add(dataSet);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

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

            mChart.setData(data);
            chartData = data;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

//        setData(seekBarX.getProgress(), seekBarY.getProgress());
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private final RectF onValueSelectedRectF = new RectF();


}
