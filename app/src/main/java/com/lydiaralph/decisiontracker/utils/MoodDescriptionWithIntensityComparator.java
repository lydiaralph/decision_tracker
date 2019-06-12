package com.lydiaralph.decisiontracker.utils;

import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;

import java.util.Comparator;

public class MoodDescriptionWithIntensityComparator implements Comparator<MoodDescriptionWithIntensity> {

    @Override
    public int compare(MoodDescriptionWithIntensity o1, MoodDescriptionWithIntensity o2) {
        if (o1 == null || o1.getDescription() == null) {
            return -1;
        }
        if (o2 == null || o2.getDescription() == null) {
            return 1;
        }

        int alphabetic = o1.getDescription().compareToIgnoreCase(o2.getDescription());

        if (alphabetic != 0) {
            return alphabetic;
        }

        return o1.getVoteDate().compareTo(o2.getVoteDate());
    }
}
