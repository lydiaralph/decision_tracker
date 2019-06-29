package com.lydiaralph.decisiontracker.charts;

import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SampleChartData {

    public static List<MoodDescriptionWithIntensity> getSampleChartData(){
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

        return inputData;
    }
}
