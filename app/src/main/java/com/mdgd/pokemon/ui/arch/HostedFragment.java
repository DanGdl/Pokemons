package com.mdgd.pokemon.ui.arch;

import androidx.fragment.app.Fragment;

public class HostedFragment<VIEW_MODEL extends FragmentContract.ViewModel, HOST extends FragmentContract.Host> extends Fragment implements FragmentContract.View {

    private VIEW_MODEL model;
    private HOST fragmentHost;

    // todo: copy from project
}
