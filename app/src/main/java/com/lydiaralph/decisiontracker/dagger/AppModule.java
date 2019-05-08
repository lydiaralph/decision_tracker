package com.lydiaralph.decisiontracker.dagger;

import android.app.Application;
import android.content.Context;

import com.lydiaralph.decisiontracker.DecisionTrackerApp;
import com.lydiaralph.decisiontracker.database.AppDatabase;
import com.lydiaralph.decisiontracker.database.repository.DecisionRepository;
import com.lydiaralph.decisiontracker.database.repository.OptionRepository;
import com.lydiaralph.decisiontracker.database.repository.VoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton @Provides
    Application providesApplication() {
        return application;
    }


    @Provides
    Context provideContext(DecisionTrackerApp application) {
        return application.getApplicationContext();
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


}