package com.lydiaralph.decisiontracker.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.dao.VoteDao;
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.VoteDao;
import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.util.List;

public class VoteRepository {

        private VoteDao voteDao;
        private LiveData<List<Vote>> allVotes;

        VoteRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            voteDao = db.votesDao();
            allVotes = voteDao.getAll();
        }
        LiveData<List<Vote>> getAllVotes() {
            return allVotes;
        }

        void insert(Vote vote) {
            new insertAsyncTask(voteDao).execute(vote);
        }

        private static class insertAsyncTask extends AsyncTask<Vote, Void, Void> {

            private VoteDao mAsyncTaskDao;

            insertAsyncTask(VoteDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Vote... params) {
                mAsyncTaskDao.insert(params[0]);
                return null;
            }
        }
}


