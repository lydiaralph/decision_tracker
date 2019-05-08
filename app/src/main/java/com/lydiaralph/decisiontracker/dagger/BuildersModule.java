package com.lydiaralph.decisiontracker.dagger;

import com.lydiaralph.decisiontracker.activities.MainActivity;
import com.lydiaralph.decisiontracker.activities.ViewDecisionsCategoryActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

//    @ContributesAndroidInjector(modules = ViewModelModule.class)
//    abstract ViewDecisionsCategoryActivity bindViewDecisionsCategoryActivity();

    // Add bindings for other sub-components here
}