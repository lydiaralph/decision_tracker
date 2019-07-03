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
 * Modified: 'OptionViewModel' rather than 'WordViewModel'. Migrated to AndroidX.
 */

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lydiaralph.decisiontracker.database.entity.Option;
import com.lydiaralph.decisiontracker.database.repository.OptionRepository;

import java.util.List;

import javax.inject.Inject;

public class OptionViewModel extends AndroidViewModel {

    private OptionRepository mRepository;
    private LiveData<List<Option>> mAllOptions;

    @Inject
    public OptionViewModel(Application application, OptionRepository optionRepository) {
        super(application);
        mRepository = optionRepository;
        mAllOptions = mRepository.getAllOptions();
    }

    public LiveData<List<Option>> getAllOptions() {
        return mAllOptions;
    }

    void insert(Option option) {
        mRepository.insert(option);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}