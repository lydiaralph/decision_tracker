package com.lydiaralph.decisiontracker.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.repository.DecisionRepository;

import java.util.List;

public class DecisionViewModel extends AndroidViewModel {

    private DecisionRepository mRepository;
    private LiveData<List<Decision>> mAllDecisions;

    public DecisionViewModel(Application application) {
        super(application);
        mRepository = new DecisionRepository(application);
        mAllDecisions = mRepository.getAllDecisions();
    }

    public LiveData<List<Decision>> getAllDecisions() {
        return mAllDecisions;
    }

    public void insert(Decision decision) {
        mRepository.insert(decision);
    }
}