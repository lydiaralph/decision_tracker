package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.Mood;
import com.lydiaralph.decisiontracker.database.entity.MoodType;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;
import com.lydiaralph.decisiontracker.database.viewmodel.MoodTypeViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.MoodViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

public class MoodTrackerActivity extends MenuBasedActivity {

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private MoodTypeViewModel moodTypeViewModel;
    private MoodViewModel moodViewModel;

    private static final String LOG = MoodTrackerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_10_vote_mood);
        moodTypeViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoodTypeViewModel.class);
        moodViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoodViewModel.class);

        Integer voteId = (Integer) getIntent().getExtras().get(VoteActivity.VOTE_ID);

        LinearLayout moodOptions = findViewById(R.id.mood_options);

        final Observer<List<MoodType>> moodTypeObserver = new Observer<List<MoodType>>() {
            @Override
            public void onChanged(@Nullable final List<MoodType> moodTypes) {
                for(MoodType moodType: moodTypes){
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(moodType.getDescription());
                    textView.setId(moodType.getId());

                    SeekBar intensityMeasure = new SeekBar(getApplicationContext());
                    intensityMeasure.setMax(100);
                    intensityMeasure.setMin(0);
                    intensityMeasure.setId(moodType.getId());
                    intensityMeasure.setPadding(0,0,0,10);

                    moodOptions.addView(textView);
                    moodOptions.addView(intensityMeasure);
                }
            }
        };

        moodTypeViewModel.getAllMoodTypes().observe(this, moodTypeObserver);

        View persistVoteButton = findViewById(R.id.button_save);
        persistVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    moodViewModel.insertAll(moodData);
                }

                Intent resultIntent = new Intent(MoodTrackerActivity.this, ViewDecisionsCategoryActivity.class);
                resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
                finish();
            }
        });
    }
}
