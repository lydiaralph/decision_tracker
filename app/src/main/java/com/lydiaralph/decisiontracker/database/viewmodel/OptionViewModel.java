package com.lydiaralph.decisiontracker.database.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.repository.OptionRepository;

import java.util.List;

public class OptionViewModel extends AndroidViewModel {

    private OptionRepository mRepository;
    private LiveData<List<Option>> mAllOptions;

    public OptionViewModel(Application application) {
        super(application);
        mRepository = new OptionRepository(application);
        mAllOptions = mRepository.getAllOptions();
    }

    public LiveData<List<Option>> getAllOptions() {
        return mAllOptions;
    }

    void insert(Option option) {
        mRepository.insert(option);
    }
}