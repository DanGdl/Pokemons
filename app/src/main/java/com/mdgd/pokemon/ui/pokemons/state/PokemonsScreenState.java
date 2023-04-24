package com.mdgd.pokemon.ui.pokemons.state;

import com.mdgd.mvi.states.AbstractState;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;

import java.util.ArrayList;
import java.util.List;

public class PokemonsScreenState extends AbstractState<PokemonsContract.View, PokemonsScreenState> {

    private final List<PokemonFullDataSchema> list;
    private final boolean isLoading;

    public PokemonsScreenState(List<PokemonFullDataSchema> list, boolean isLoading) {
        this.list = list;
        this.isLoading = isLoading;
    }

    @Override
    public void visit(PokemonsContract.View pokemonsScreen) {
        pokemonsScreen.setProgressVisibility(isLoading);
        pokemonsScreen.setItems(list);
    }

    public static class SetItems extends PokemonsScreenState {

        public SetItems(List<PokemonFullDataSchema> items) {
            super(items, false);
        }
    }

    public static class AddItems extends PokemonsScreenState {

        public AddItems(List<PokemonFullDataSchema> items) {
            super(items, false);
        }

        @Override
        public PokemonsScreenState merge(PokemonsScreenState prevState) {
            // TODO: impl
            return super.merge(prevState);
        }
    }

    public static class SetProgressVisibility extends PokemonsScreenState {

        public SetProgressVisibility(boolean isLoading) {
            super(new ArrayList<>(0), isLoading);
        }
    }
}
