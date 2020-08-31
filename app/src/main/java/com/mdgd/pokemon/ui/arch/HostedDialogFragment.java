package com.mdgd.pokemon.ui.arch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;

import java.lang.reflect.ParameterizedType;

public abstract class HostedDialogFragment<STATE extends ScreenState, VIEW_MODEL extends FragmentContract.ViewModel<STATE>, HOST extends FragmentContract.Host>
        extends AppCompatDialogFragment implements FragmentContract.View, Observer<STATE> {

    private VIEW_MODEL model;
    private HOST fragmentHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // keep the call back
        try {
            this.fragmentHost = (HOST) context;
        } catch (Throwable e) {
            final String hostClassName = ((Class) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[1]).getCanonicalName();
            throw new RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // release the call back
        this.fragmentHost = null;
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

    @Override
    public void onChanged(STATE state) {

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

    protected abstract VIEW_MODEL createModel();

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
