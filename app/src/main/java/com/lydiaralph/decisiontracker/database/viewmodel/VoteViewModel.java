package com.lydiaralph.decisiontracker.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.repository.VoteRepository;

import java.util.List;

public class VoteViewModel extends AndroidViewModel {

    private VoteRepository mRepository;
    private LiveData<List<Vote>> mAllVotes;

    public VoteViewModel(Application application) {
        super(application);
        mRepository = new VoteRepository(application);
        mAllVotes = mRepository.getAllVotes();
    }

    LiveData<List<Vote>> getAllVotes() {
        return mAllVotes;
    }

    void insert(Vote vote) {
        mRepository.insert(vote);
    }
}