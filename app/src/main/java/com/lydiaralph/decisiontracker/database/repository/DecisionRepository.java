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
import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;

import java.util.List;

public class DecisionRepository {

        private DecisionDao decisionDao;
        private LiveData<List<Decision>> allDecisions;

        public DecisionRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            decisionDao = db.decisionDao();
            allDecisions = decisionDao.getAll();
        }
        public LiveData<List<Decision>> getAllDecisions() {
            return allDecisions;
        }

        public LiveData<DecisionOptions> getDecisionById(Integer decisionId) {
            return decisionDao.getDecisionById(decisionId);
        }

        public void insert(Decision decision) {
            new insertAsyncTask(decisionDao).execute(decision);
        }

        private static class insertAsyncTask extends AsyncTask<Decision, Void, Void> {

            private DecisionDao mAsyncTaskDao;

            insertAsyncTask(DecisionDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Decision... params) {
                mAsyncTaskDao.insert(params[0]);
                return null;
            }
        }
}


