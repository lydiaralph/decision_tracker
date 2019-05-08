package com.lydiaralph.decisiontracker.dagger;

import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.OptionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.VoteViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DecisionViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(DecisionViewModel.class)
    abstract ViewModel provideDecisionViewModel(DecisionViewModel decisionViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VoteViewModel.class)
    abstract ViewModel provideVoteViewModel(VoteViewModel voteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OptionViewModel.class)
    abstract ViewModel provideOptionViewModel(OptionViewModel optionViewModel);
}
