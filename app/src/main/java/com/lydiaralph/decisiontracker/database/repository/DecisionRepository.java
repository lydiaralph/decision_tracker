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
import android.os.AsyncTask;
import android.os.LocaleList;

import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionInsert;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.Option;

import java.time.LocalDate;
import java.util.List;

import androidx.lifecycle.LiveData;

public class DecisionRepository {

    private DecisionDao decisionDao;
    private OptionDao optionDao;
    private LiveData<List<Decision>> allDecisions;

    public DecisionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        decisionDao = db.decisionDao();
        optionDao = db.optionDao();
        allDecisions = decisionDao.getAll();
    }

    public void deleteAll(){
        new deleteAsyncTask(decisionDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Decision, Void, Void> {
        private DecisionDao mAsyncTaskDao;

        deleteAsyncTask(DecisionDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Decision... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public LiveData<List<Decision>> getAllDecisions() {
        return allDecisions;
    }

    public LiveData<DecisionOptions> getDecisionById(Integer decisionId) {
        return decisionDao.getDecisionById(decisionId);
    }

    public void updateEndDate(int decisionId, LocalDate newExpiryDate) {
        new updateAsyncTask(decisionDao).execute(new DecisionIdAndEndDate(decisionId, newExpiryDate));
    }

    public void insert(DecisionInsert decision) {
        new insertAsyncTask(decisionDao, optionDao).execute(decision);
    }

    private static class insertAsyncTask extends AsyncTask<DecisionInsert, Void, Void> {

        private DecisionDao mAsyncTaskDao;
        private OptionDao mAsyncTaskOptionDao;

        insertAsyncTask(DecisionDao dao, OptionDao optionDao) {
            mAsyncTaskDao = dao;
            mAsyncTaskOptionDao = optionDao;
        }

        @Override
        protected Void doInBackground(final DecisionInsert... params) {
            long decisionId = mAsyncTaskDao.insert(params[0].getDecision());
            if (!params[0].getOptionTextList().isEmpty()) {
                for (String optionText : params[0].getOptionTextList()) {
                    Option optionWithDecisionId = new Option(decisionId, optionText);
                    mAsyncTaskOptionDao.insert(optionWithDecisionId);
                }
            }
            return null;
        }
    }

    private class DecisionIdAndEndDate {
        private int decisionId;
        private LocalDate endDate;

        public DecisionIdAndEndDate(int decisionId, LocalDate endDate){
            this.decisionId = decisionId;
            this.endDate = endDate;
        }

        public int getDecisionId() {
            return decisionId;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }

    private static class updateAsyncTask extends AsyncTask<DecisionIdAndEndDate, Void, Void> {

        private DecisionDao mAsyncDecisionDao;

        updateAsyncTask(DecisionDao dao) {
            mAsyncDecisionDao = dao;
        }

        @Override
        protected Void doInBackground(final DecisionIdAndEndDate... params) {
            mAsyncDecisionDao.updateEndDate(params[0].getDecisionId(), params[0].getEndDate());
            return null;
        }
    }
}


