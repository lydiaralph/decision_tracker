package com.lydiaralph.decisiontracker.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.MoodTypeDao;
import com.lydiaralph.decisiontracker.database.entity.MoodType;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MoodTypeRepository {

        private MoodTypeDao dao;
        private LiveData<List<MoodType>> allMoodTypes;

        public MoodTypeRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            dao = db.moodTypeDao();
            allMoodTypes = dao.getAll();
        }

        public LiveData<List<MoodType>> getAllMoodTypes() {
            return allMoodTypes;
        }


    public void deleteAll(){
        new deleteAsyncTask(dao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<MoodType, Void, Void> {
        private MoodTypeDao mAsyncTaskDao;

        deleteAsyncTask(MoodTypeDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final MoodType... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void insert(MoodType moodType) {
            new insertAsyncTask(dao).execute(moodType);
        }

        private static class insertAsyncTask extends AsyncTask<MoodType, Void, Void> {

            private MoodTypeDao mAsyncTaskDao;

            insertAsyncTask(MoodTypeDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final MoodType... params) {
                mAsyncTaskDao.insert(params[0]);
                return null;
            }
        }
}


