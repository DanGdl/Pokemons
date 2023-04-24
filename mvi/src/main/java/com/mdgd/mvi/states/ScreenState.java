package com.mdgd.mvi.states;

public interface ScreenState<V> {

    void visit(V screen);
}
