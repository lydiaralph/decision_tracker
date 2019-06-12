package com.lydiaralph.decisiontracker.database.viewmodel;

import android.app.Application;

import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.Mood;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;
import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.repository.MoodRepository;
import com.lydiaralph.decisiontracker.database.repository.OptionRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoodViewModel extends AndroidViewModel {

    private MoodRepository mRepository;

    @Inject
    public MoodViewModel(Application application, MoodRepository repository) {
        super(application);
        mRepository = repository;
    }

    public void insertAll(List<Mood> moodList) {
        mRepository.insertAll(moodList);
    }

    public LiveData<List<MoodDescriptionWithIntensity>> getAllMoodsByDecisionId(Integer decisionId) {
        return mRepository.getAllMoodsByDecisionId(decisionId);
    }
}