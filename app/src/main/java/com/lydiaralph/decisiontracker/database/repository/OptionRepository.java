package com.lydiaralph.decisiontracker.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.entity.Option;

import java.util.List;

public class OptionRepository {

        private OptionDao optionDao;
        private LiveData<List<Option>> allOptions;

        OptionRepository(Application application) {
            AppDatabase db = AppDatabase.getDatabase(application);
            optionDao = db.optionsDao();
            allOptions = optionDao.getAll();
        }
        LiveData<List<Option>> getAllOptions() {
            return allOptions;
        }

        void insert(Option option) {
            new insertAsyncTask(optionDao).execute(option);
        }

        private static class insertAsyncTask extends AsyncTask<Option, Void, Void> {

            private OptionDao mAsyncTaskDao;

            insertAsyncTask(OptionDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Option... params) {
                mAsyncTaskDao.insertAll(params);
                return null;
            }
        }
}


