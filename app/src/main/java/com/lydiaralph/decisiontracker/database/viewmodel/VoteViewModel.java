package com.lydiaralph.decisiontracker.database.viewmodel;

/*
 * Copyright (C) 2019 Lydia Ralph
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
 *
 * Derived from https://github.com/googlecodelabs/android-room-with-a-view
 *
 * Modified: 'VoteViewModel' rather than 'WordViewModel'. Migrated to AndroidX.
 */

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.repository.VoteRepository;

import java.util.List;

import javax.inject.Inject;

public class VoteViewModel extends AndroidViewModel {

    private VoteRepository mRepository;
    private LiveData<List<Vote>> mAllVotes;

    @Inject
    public VoteViewModel(Application application, VoteRepository voteRepository) {
        super(application);
        mRepository = voteRepository;
        mAllVotes = mRepository.getAllVotes();
    }

    public LiveData<List<Vote>> getAllVotes() {
        return mAllVotes;
    }

    public void insert(Vote vote) {
        mRepository.insert(vote);
    }
}