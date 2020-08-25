package com.mdgd.pokemon.ui.splash;

import com.mdgd.pokemon.ui.arch.HostedFragment;

public class SplashFragment extends HostedFragment<SplashContract.ViewModel, SplashContract.Host> implements SplashContract.View {

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }
}
