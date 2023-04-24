package com.mdgd.pokemon.ui.pokemon.state;

import com.mdgd.mvi.states.AbstractState;
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract;
import com.mdgd.pokemon.ui.pokemon.items.PokemonProperty;

import java.util.List;

public class PokemonScreenState extends AbstractState<PokemonDetailsContract.View, PokemonScreenState> {

    protected final List<PokemonProperty> items;

    public PokemonScreenState(List<PokemonProperty> items) {
        this.items = items;
    }

    public void visit(PokemonDetailsContract.View screen) {
        screen.setItems(items);
    }


    public static class SetItems extends PokemonScreenState {

        public SetItems(List<PokemonProperty> items) {
            super(items);
        }

    }
}
