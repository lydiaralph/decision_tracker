package com.lydiaralph.decisiontracker.dagger;

import android.app.Application;
import android.content.Context;

import com.lydiaralph.decisiontracker.DecisionTrackerApp;

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

}