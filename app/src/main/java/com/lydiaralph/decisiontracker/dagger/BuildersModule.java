package com.lydiaralph.decisiontracker.dagger;

import com.lydiaralph.decisiontracker.activities.ConfigureNewDecisionActivity;
import com.lydiaralph.decisiontracker.activities.MainActivity;
import com.lydiaralph.decisiontracker.activities.MoodTrackerActivity;
import com.lydiaralph.decisiontracker.activities.ViewDecisionDetailActivity;
import com.lydiaralph.decisiontracker.activities.ViewDecisionsCategoryActivity;
import com.lydiaralph.decisiontracker.activities.VoteActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract ViewDecisionsCategoryActivity bindViewDecisionsCategoryActivity();

    @ContributesAndroidInjector
    abstract VoteActivity bindVoteActivity();

    @ContributesAndroidInjector
    abstract MoodTrackerActivity bindMoodTrackerActivity();

    @ContributesAndroidInjector
    abstract ViewDecisionDetailActivity bindViewDecisionDetailActivity();

    @ContributesAndroidInjector
    abstract ConfigureNewDecisionActivity bindConfigureNewDecisionActivity();
}