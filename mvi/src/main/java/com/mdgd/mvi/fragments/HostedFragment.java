package com.mdgd.mvi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.mdgd.mvi.states.ScreenState;

import java.lang.reflect.ParameterizedType;

public abstract class HostedFragment<
        VIEW extends FragmentContract.View,
        VIEW_MODEL extends FragmentContract.ViewModel<VIEW>,
        HOST extends FragmentContract.Host
        > extends NavHostFragment implements FragmentContract.View, Observer<ScreenState<VIEW>>, LifecycleEventObserver {

    private VIEW_MODEL viewModel;
    private HOST fragmentHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // keep the call back
        try {
            fragmentHost = (HOST) context;
        } catch (Throwable e) {
            final String hostClassName = ((Class) ((ParameterizedType) getClass().
                    getGenericSuperclass()).getActualTypeArguments()[1]).getCanonicalName();
            throw new RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + this.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(createModel());
        getLifecycle().addObserver(this);
        if (getViewModel() != null) {
            getViewModel().getStateObservable().observe(this, this);
            getViewModel().getEffectObservable().observe(this, this);
        }
    }

    protected abstract VIEW_MODEL createModel();

    public void onStateChanged(@NonNull LifecycleOwner owner, @NonNull Lifecycle.Event event) {
        if (getViewModel() != null) {
            getViewModel().onStateChanged(event);
        }
        if (getLifecycle().getCurrentState().ordinal() == Lifecycle.State.DESTROYED.ordinal()) {
            getLifecycle().removeObserver(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // release the call back
        fragmentHost = null;
    }

    @Override
    public void onChanged(ScreenState<VIEW> screenState) {
        screenState.visit((VIEW) this);
    }

    protected boolean hasHost() {
        return fragmentHost != null;
    }

    protected HOST getFragmentHost() {
        return fragmentHost;
    }

    protected VIEW_MODEL getViewModel() {
        return viewModel;
    }

    protected void setViewModel(VIEW_MODEL viewModel) {
        this.viewModel = viewModel;
    }
}
