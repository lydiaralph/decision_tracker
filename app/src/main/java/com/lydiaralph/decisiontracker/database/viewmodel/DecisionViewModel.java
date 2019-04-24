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
 * Modified: 'DecisionViewModel' rather than 'WordViewModel'. Migrated to AndroidX.
 */


import android.app.Application;

import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionInsert;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.repository.DecisionRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    public LiveData<DecisionOptions> getDecisionById(Integer decisionId) {
        return mRepository.getDecisionById(decisionId);
    }

    public void insert(DecisionInsert decision) {
        mRepository.insert(decision);
    }
}