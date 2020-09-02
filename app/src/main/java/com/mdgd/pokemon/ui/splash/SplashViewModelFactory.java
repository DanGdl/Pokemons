package com.mdgd.pokemon.ui.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.models.AppComponent;
import com.mdgd.pokemon.models.cache.Cache;

import javax.inject.Inject;

public class SplashViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SplashContract.Router router;
    @Inject
    public Cache cache;

    public SplashViewModelFactory(AppComponent appComponent, SplashContract.Router router) {
        super();
        this.router = router;
        appComponent.injectPokemonsCache(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SplashViewModel.class) {
            return (T) new SplashViewModel(router, cache);
        }
        return null;
    }
}
