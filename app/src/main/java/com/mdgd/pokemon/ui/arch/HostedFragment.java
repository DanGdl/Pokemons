package com.mdgd.pokemon.ui.arch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import java.lang.reflect.ParameterizedType;

public abstract class HostedFragment<STATE extends ScreenState, VIEW_MODEL extends FragmentContract.ViewModel<STATE>, HOST extends FragmentContract.Host>
        extends NavHostFragment
        implements FragmentContract.View, Observer<STATE> {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(createModel());
        if (getModel() != null) {
            getLifecycle().addObserver(getModel());
            getModel().getStateObservable().observe(this, this);
        }
    }

    protected abstract VIEW_MODEL createModel();

    @Override
    public void onDetach() {
        super.onDetach();
        // release the call back
        fragmentHost = null;
    }

    @Override
    public void onDestroy() {
        // order matters
        if (getModel() != null) {
            getModel().getStateObservable().removeObservers(this);
            getLifecycle().removeObserver(getModel());
        }
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
