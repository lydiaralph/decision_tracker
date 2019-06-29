package com.lydiaralph.decisiontracker.charts;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartColors {

    public static List<Integer> getColors(){
        List<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }

}
