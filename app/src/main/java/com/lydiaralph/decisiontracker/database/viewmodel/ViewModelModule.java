package com.lydiaralph.decisiontracker.database.viewmodel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DecisionViewModel.class)
    abstract ViewModel bindUserViewModel(DecisionViewModel decisionViewModel);
}
