package com.mdgd.pokemon.ui;

import android.os.Bundle;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.arch.HostActivity;
import com.mdgd.pokemon.ui.splash.SplashFragment;

public class MainActivity extends HostActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment(SplashFragment.newInstance());
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
