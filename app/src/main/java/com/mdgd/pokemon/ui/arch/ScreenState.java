package com.mdgd.pokemon.ui.arch;

public abstract class ScreenState<T extends FragmentContract.View> {

    public abstract void visit(T screen);
}
