package com.lydiaralph.decisiontracker.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

public class DecisionRepository {

        private DecisionDao decisionDao;
        private LiveData<List<Decision>> allDecisions;

        public DecisionRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            decisionDao = db.decisionsDao();
            allDecisions = decisionDao.getAll();
        }
        public LiveData<List<Decision>> getAllDecisions() {
            return allDecisions;
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


