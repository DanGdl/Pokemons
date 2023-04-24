package com.mdgd.mvi.states;

public class AbstractEffect<V> implements ScreenState<V> {
    private boolean isHandled = false;

    @Override
    public final void visit(V screen) {
        if (!isHandled) {
            handle(screen);
            isHandled = true;
        }
    }

    protected void handle(V screen) {

    }
}
