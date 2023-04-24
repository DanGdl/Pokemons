package com.mdgd.pokemon.ui.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.mdgd.mvi.fragments.HostedFragment;
import com.mdgd.pokemon.PokemonsApp;
import com.mdgd.pokemon.R;
import com.mdgd.pokemon.bg.UploadWorker;

public class SplashFragment extends HostedFragment<SplashContract.View, SplashContract.ViewModel, SplashContract.Host> implements SplashContract.View {

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    protected SplashContract.ViewModel createModel() {
        return new ViewModelProvider(this, new SplashViewModelFactory(PokemonsApp.getInstance().getAppComponent())).get(SplashViewModel.class);
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

    @Override
    public void launchWorker() {
        if (hasHost()) {
            final WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
            WorkManager.getInstance(getContext()).enqueue(uploadWorkRequest);
        }
    }

    @Override
    public void showError(Throwable error) {
        if (hasHost()) {
            getFragmentHost().showError(error);
        }
    }
}
