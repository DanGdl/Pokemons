package com.mdgd.pokemon.ui.arch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.lang.reflect.ParameterizedType;

public abstract class HostedFragment<STATE extends ScreenState, VIEW_MODEL extends FragmentContract.ViewModel<STATE>, HOST extends FragmentContract.Host> extends Fragment
        implements FragmentContract.View, Observer<STATE>, Screen {

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

    @Override
    public void onDestroy() {
        // order matters
        getModel().getStateObservable().removeObservers(this);
        getLifecycle().removeObserver(getModel());
        super.onDestroy();
    }


    @Override
    public void onChanged(STATE screenState) {
        screenState.visit(this);
    }

    protected boolean hasHost() {
        return fragmentHost != null;
    }

    protected HOST getFragmentHost() {
        return fragmentHost;
    }

    protected VIEW_MODEL getModel() {
        return model;
    }

    protected void setModel(VIEW_MODEL model) {
        this.model = model;
    }
}
