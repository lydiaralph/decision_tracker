package com.lydiaralph.decisiontracker.charts;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class LineChartDisplayTest {

    @Test
    public void testTransformationOfInputDataToLineData() {
        MoodDescriptionWithIntensity mood1 = new MoodDescriptionWithIntensity(1,
                LocalDate.now(), "Zgew", 23);

        MoodDescriptionWithIntensity mood1b = new MoodDescriptionWithIntensity(3,
                LocalDate.now(), "Zgew", 53);

        MoodDescriptionWithIntensity mood2 = new MoodDescriptionWithIntensity(1,
                LocalDate.now(), "Abdss", 23);

        MoodDescriptionWithIntensity mood2b = new MoodDescriptionWithIntensity(2,
                LocalDate.now(), "Abdss", 74);

        MoodDescriptionWithIntensity mood2c = new MoodDescriptionWithIntensity(3,
                LocalDate.now(), "Abdss", 85);

        MoodDescriptionWithIntensity mood2d = new MoodDescriptionWithIntensity(3,
                LocalDate.now(), "Abdss", null);

        MoodDescriptionWithIntensity mood3 = new MoodDescriptionWithIntensity(1,
                LocalDate.now(), "geowig", 23);


        List<MoodDescriptionWithIntensity> inputData = Arrays.asList(
                mood2b, mood3, mood1b, mood2c, mood2d, mood1, mood2);

        LineData returnedDataSet = LineChartDisplay.setData(inputData);

        assertThat(returnedDataSet.getDataSetCount(), equalTo(3));

        assertThat(returnedDataSet.getDataSetByIndex(0).getLabel(), equalTo("Abdss"));
        assertThat(returnedDataSet.getDataSetByIndex(1).getLabel(), equalTo("geowig"));
        assertThat(returnedDataSet.getDataSetByIndex(2).getLabel(), equalTo("Zgew"));

        ILineDataSet firstDataSet = returnedDataSet.getDataSetByIndex(0);
        assertThat("Null mood intensity should be ignored",
                firstDataSet.getEntryCount(), equalTo(3));

        assertThat(firstDataSet.getEntryForIndex(0).getX(), equalTo(18055f));
        assertThat(firstDataSet.getEntryForIndex(0).getY(), equalTo(74f));

        assertThat(firstDataSet.getEntryForIndex(1).getY(), equalTo(85f));
        assertThat(firstDataSet.getEntryForIndex(2).getY(), equalTo(23f));
    }
}
