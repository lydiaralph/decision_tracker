package com.lydiaralph.decisiontracker.database.repository;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.util.List;

/**
 * View Model to keep a reference to the Vote repository and
 * an up-to-date list of all Votes.
 */

public class VoteViewModel extends AndroidViewModel {

    private VoteRepository mRepository;
    // Using LiveData and caching what getAlphabetizedVotes returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
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