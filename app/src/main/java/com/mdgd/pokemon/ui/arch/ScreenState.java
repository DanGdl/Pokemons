package com.mdgd.pokemon.ui.arch;

public abstract class ScreenState<T extends Screen> {

    public abstract void visit(T screen);
}
