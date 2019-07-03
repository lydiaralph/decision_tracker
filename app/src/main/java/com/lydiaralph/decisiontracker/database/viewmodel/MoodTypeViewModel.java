package com.lydiaralph.decisiontracker.database.viewmodel;

import android.app.Application;

import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionInsert;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.MoodType;
import com.lydiaralph.decisiontracker.database.repository.DecisionRepository;
import com.lydiaralph.decisiontracker.database.repository.MoodTypeRepository;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoodTypeViewModel extends AndroidViewModel {

    private MoodTypeRepository mRepository;
    private LiveData<List<MoodType>> mAllMoodTypes;

    @Inject
    public MoodTypeViewModel(Application application,
                             MoodTypeRepository repository) {
        super(application);
        mRepository = repository;
        mAllMoodTypes = mRepository.getAllMoodTypes();
    }

    public LiveData<List<MoodType>> getAllMoodTypes() {
        return mAllMoodTypes;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}