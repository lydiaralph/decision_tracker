package com.lydiaralph.decisiontracker.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.Mood;
import com.lydiaralph.decisiontracker.database.entity.MoodType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoodTrackerActivity extends MenuBasedActivity{

    private static final String LOG = MoodTrackerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_10_vote_mood);

        LinearLayout moodOptions = findViewById(R.id.mood_options);

        // TODO: Replace with dynamic database query
        List<MoodType> moodTypes = Arrays.asList(
                new MoodType(1, "Angry"),
                new MoodType(2, "Sad"),
                new MoodType(3, "Happy"),
                new MoodType(4, "Relaxed"));

        for(MoodType moodType: moodTypes){
            TextView textView = new TextView(this);
            textView.setText(moodType.getDescription());
            textView.setId(moodType.getId());

            SeekBar intensityMeasure = new SeekBar(this);
            intensityMeasure.setMax(100);
            intensityMeasure.setMin(0);
            intensityMeasure.setId(moodType.getId());
            intensityMeasure.setPadding(0,0,0,10);

            moodOptions.addView(textView);
            moodOptions.addView(intensityMeasure);
        }

        // On Save button click, get all moods and their associated intensities and persist in Mood table
        // (vote_id), mood_id, intensity
        View persistVoteButton = findViewById(R.id.button_save);
        persistVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Get vote ID from calling intent
                Integer voteId = 1;

                List<Mood> moodData = new ArrayList<>();
                int count = moodOptions.getChildCount();

                if(count > 1) {
                    for (int i = 1; i < count; i = i + 2) {
                        SeekBar moodTrackerVote = (SeekBar) ((LinearLayout) moodOptions).getChildAt(i);
                        Integer moodId = moodTrackerVote.getId();
                        Integer intensity = moodTrackerVote.getProgress();
                        Mood mood = new Mood(voteId, moodId, intensity);
                        moodData.add(mood);
                    }

//                // Persist in mood table
//                    voteViewModel.insert(moodData);
                }
//
//                setResult(Activity.RESULT_OK, resultIntent);
//                startActivity(resultIntent);
                finish();
            }
        });

        // Round 2: get Vote ID from calling intent

    }
}
