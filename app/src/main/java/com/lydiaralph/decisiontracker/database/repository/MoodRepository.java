package com.lydiaralph.decisiontracker.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.MoodDao;
import com.lydiaralph.decisiontracker.database.entity.Mood;

import java.util.List;

public class MoodRepository {

        private MoodDao dao;

        public MoodRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            dao = db.moodDao();
        }

        public void insert(Mood mood) {
            new insertAsyncTask(dao).execute(mood);
        }

        private static class insertAsyncTask extends AsyncTask<Mood, Void, Void> {

            private MoodDao mAsyncTaskDao;

            insertAsyncTask(MoodDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Mood... params) {
                mAsyncTaskDao.insert(params[0]);
                return null;
            }
        }

    public void insertAll(List<Mood> mood) {
        new insertAllAsyncTask(dao).execute(mood);
    }

    private static class insertAllAsyncTask extends AsyncTask<List<Mood>, Void, Void> {

        private MoodDao mAsyncTaskDao;

        insertAllAsyncTask(MoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Mood>... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}


