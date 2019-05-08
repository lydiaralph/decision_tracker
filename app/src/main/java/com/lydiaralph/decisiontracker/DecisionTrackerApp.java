package com.lydiaralph.decisiontracker;

import android.app.Activity;
import android.app.Application;

import com.lydiaralph.decisiontracker.dagger.DaggerAppComponent;

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
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}