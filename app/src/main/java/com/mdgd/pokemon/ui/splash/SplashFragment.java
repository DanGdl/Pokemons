package com.mdgd.pokemon.ui.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.arch.HostedFragment;

public class SplashFragment extends HostedFragment<SplashScreenState, SplashContract.ViewModel, SplashContract.Host> implements SplashContract.View, SplashContract.Router {

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(new ViewModelProvider(this, new SplashViewModelFactory(this)).get(SplashViewModel.class));
        getLifecycle().addObserver(getModel());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void proceedToNextScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToPokemonsScreen();
        }
    }
}
