package com.mdgd.pokemon.ui.error;

import com.mdgd.pokemon.ui.arch.FragmentContract;

public class ErrorContract {

    public interface ViewModel extends FragmentContract.ViewModel<ErrorFragmentState> {
    }

    public interface View extends FragmentContract.View {
    }

    public interface Host extends FragmentContract.Host {
    }
}
