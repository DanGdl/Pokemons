package com.mdgd.mvi.states;

public abstract class AbstractState<V, S> implements ScreenState<V> {
    @Override
    public void visit(V screen) {

    }

    public S merge(S prevState) {
        return (S) this;
    }
}
