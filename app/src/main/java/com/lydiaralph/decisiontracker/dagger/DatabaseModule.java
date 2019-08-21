package com.lydiaralph.decisiontracker.dagger;

import android.app.Application;

import com.lydiaralph.decisiontracker.DecisionTrackerApp;
import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.repository.DecisionRepository;
import com.lydiaralph.decisiontracker.database.repository.MoodRepository;
import com.lydiaralph.decisiontracker.database.repository.MoodTypeRepository;
import com.lydiaralph.decisiontracker.database.repository.OptionRepository;
import com.lydiaralph.decisiontracker.database.repository.VoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private Application application;

    public DatabaseModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(DecisionTrackerApp application) {
        return AppDatabase.getDatabase(application.getApplicationContext());
    }

    @Singleton
    @Provides
    DecisionRepository providesDecisionRepository(){
        return new DecisionRepository(application);
    }

    @Singleton
    @Provides
    VoteRepository providesVoteRepository(){
        return new VoteRepository(application);
    }

    @Singleton
    @Provides
    OptionRepository providesOptionRepository(){
        return new OptionRepository(application);
    }

    @Singleton
    @Provides
    MoodTypeRepository providesMoodTypeRepository(){
        return new MoodTypeRepository(application);
    }

    @Singleton
    @Provides
    MoodRepository providesMoodRepository(){
        return new MoodRepository(application);
    }
}