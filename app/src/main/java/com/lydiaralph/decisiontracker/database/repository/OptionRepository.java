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

    public OptionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        optionDao = db.optionsDao();
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
            mAsyncTaskDao.insertAll(params);
            return null;
        }
    }
}


