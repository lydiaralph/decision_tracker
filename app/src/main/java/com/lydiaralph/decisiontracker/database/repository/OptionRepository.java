package com.lydiaralph.decisiontracker.database.repository;

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
 * Modified: 'DecisionRepository' rather than 'WordRepository'. Migrated to AndroidX.
 */


import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.entity.Option;

import java.util.List;

public class OptionRepository {

    private OptionDao optionDao;
    private LiveData<List<Option>> allOptions;

    public OptionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        optionDao = db.optionDao();
        allOptions = optionDao.getAll();
    }

    public LiveData<List<Option>> getAllOptions() {
        return allOptions;
    }

    public void insert(Option option) {
        new insertAsyncTask(optionDao).execute(option);
    }

    private static class insertAsyncTask extends AsyncTask<Option, Void, Void> {

        private OptionDao mAsyncTaskDao;

        insertAsyncTask(OptionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Option... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}


