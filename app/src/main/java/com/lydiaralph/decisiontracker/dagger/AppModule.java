package com.lydiaralph.decisiontracker.dagger;

import android.content.Context;


import com.lydiaralph.decisiontracker.DecisionTrackerApp;
import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.dao.DecisionDao;
import com.lydiaralph.decisiontracker.database.dao.OptionDao;
import com.lydiaralph.decisiontracker.database.dao.VoteDao;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(DecisionTrackerApp application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(DecisionTrackerApp application) {
        return AppDatabase.getDatabase(application.getApplicationContext());
    }



}