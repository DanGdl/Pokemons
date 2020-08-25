package com.mdgd.pokemon.ui.arch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.ParameterizedType;

public abstract class HostedFragment<VIEW_MODEL extends FragmentContract.ViewModel, HOST extends FragmentContract.Host> extends Fragment
        implements FragmentContract.View {

    private VIEW_MODEL model;
    private HOST fragmentHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // keep the call back
        try {
            fragmentHost = (HOST) context;
        } catch (Throwable e) {
            final String hostClassName = ((Class) ((ParameterizedType) getClass().
                    getGenericSuperclass())
                    .getActualTypeArguments()[1]).getCanonicalName();
            throw new RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + this.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // release the call back
        fragmentHost = null;
    }

    protected boolean hasHost() {
        return fragmentHost != null;
    }

    protected HOST getFragmentHost() {
        return fragmentHost;
    }
}
