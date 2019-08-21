package com.lydiaralph.decisiontracker;

import android.app.Activity;
import android.app.Application;

import com.lydiaralph.decisiontracker.dagger.AppModule;
import com.lydiaralph.decisiontracker.dagger.DaggerAppComponent;
import com.lydiaralph.decisiontracker.dagger.DatabaseModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class DecisionTrackerApp extends Application implements HasActivityInjector {

    private static DecisionTrackerApp instance;

    public static DecisionTrackerApp getInstance() {
        return instance;
    }

    public static void setInstance(DecisionTrackerApp instance) {
        DecisionTrackerApp.instance = instance;
    }

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}