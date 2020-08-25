package com.mdgd.pokemon.ui.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SplashViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SplashContract.Router router;

    public SplashViewModelFactory(SplashContract.Router router) {
        super();
        this.router = router;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SplashViewModel.class) {
            return (T) new SplashViewModel(router);
        }
        return null;
    }
}
