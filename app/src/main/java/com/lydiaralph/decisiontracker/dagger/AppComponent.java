package com.lydiaralph.decisiontracker.dagger;

import com.lydiaralph.decisiontracker.DecisionTrackerApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        DatabaseModule.class,
        ViewModelModule.class,
        BuildersModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(DecisionTrackerApp application);
        Builder appModule(AppModule appModule);
        Builder databaseModule(DatabaseModule databaseModule);
        AppComponent build();
    }
    void inject(DecisionTrackerApp application);
}