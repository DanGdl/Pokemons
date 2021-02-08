package com.mdgd.pokemon.ui.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.models.AppModule;

public class SplashViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SplashContract.Router router;
    private final AppModule appComponent;

    public SplashViewModelFactory(AppModule appComponent, SplashContract.Router router) {
        super();
        this.appComponent = appComponent;
        this.router = router;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SplashViewModel.class) {
            return (T) new SplashViewModel(router, appComponent.getCache());
        }
        return null;
    }
}
