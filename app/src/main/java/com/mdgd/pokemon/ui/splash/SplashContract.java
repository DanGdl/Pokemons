package com.mdgd.pokemon.ui.splash;

import com.mdgd.mvi.fragments.FragmentContract;

public class SplashContract {

    public interface ViewModel extends FragmentContract.ViewModel<View> {
    }

    public interface View extends FragmentContract.View {
        void proceedToNextScreen();

        void launchWorker();

        void showError(Throwable error);
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPokemonsScreen();

        void showError(Throwable error);
    }
}
