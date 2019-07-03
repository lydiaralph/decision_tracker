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

import com.lydiaralph.decisiontracker.database.dao.VoteDao;
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class VoteRepository {

    private VoteDao voteDao;
    private LiveData<List<Vote>> allVotes;

    public VoteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        voteDao = db.voteDao();
        allVotes = voteDao.getAll();
    }

    public void deleteAll(){
        new deleteAsyncTask(voteDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Vote, Void, Void> {
        private VoteDao mAsyncTaskDao;

        deleteAsyncTask(VoteDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Vote... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public LiveData<List<Vote>> getAllVotes() {
        return allVotes;
    }

    public Long insert(Vote vote) throws ExecutionException, InterruptedException {
        return new insertAsyncTask(voteDao).execute(vote).get();
    }

    private static class insertAsyncTask extends AsyncTask<Vote, Void, Long> {

        private VoteDao mAsyncTaskDao;

        insertAsyncTask(VoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Vote... params) {
            return mAsyncTaskDao.insert(params[0]);
        }
    }
}


