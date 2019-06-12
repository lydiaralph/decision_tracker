package com.lydiaralph.decisiontracker.charts;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;

import java.util.ArrayList;
import java.util.List;

public class SimpleListDisplay implements ChartDisplay<List<String>> {

    private ArrayAdapter adapter;
    private ListView listView;

    public SimpleListDisplay(ArrayAdapter adapter, ListView listView) {
        this.adapter = adapter;
        this.listView = listView;
    }


    public static List<String> setData(List<MoodDescriptionWithIntensity> moodDescriptionWithIntensities) {
        List<String> arrayOfMoods = new ArrayList<>();

        arrayOfMoods.add("Option ID: Vote ID: Mood: Intensity");

        for (MoodDescriptionWithIntensity moodEntry : moodDescriptionWithIntensities) {
            String moodTypeDescription = moodEntry.getDescription();
            String intensity = moodEntry.getMoodIntensity() == null ? "null" :
                    moodEntry.getMoodIntensity().toString();
            String optionId = moodEntry.getOptionId() == null ? "null" :
                    moodEntry.getOptionId().toString();
            String voteDate = moodEntry.getVoteDate() == null ? "null" :
                    moodEntry.getVoteDate().toString();

            arrayOfMoods.add(String.join(": ", optionId,
                    voteDate, moodTypeDescription, intensity));
        }
        return arrayOfMoods;
    }

    @Override
    public void displayData(List<String> dataSet) {
        listView.setAdapter(adapter);
    }
}
