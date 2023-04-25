package com.mdgd.pokemon.ui.pokemons.state;

import com.mdgd.mvi.states.AbstractState;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;

import java.util.ArrayList;
import java.util.List;

public class PokemonsScreenState extends AbstractState<PokemonsContract.View, PokemonsScreenState> {

    protected final List<PokemonFullDataSchema> list;
    protected final List<String> availableFilters;
    protected final List<String> activeFilters;
    protected final boolean isLoading;

    public PokemonsScreenState(
            List<PokemonFullDataSchema> list, boolean isLoading,
            List<String> availableFilters, List<String> activeFilters
    ) {
        this.list = list;
        this.isLoading = isLoading;
        this.availableFilters = availableFilters;
        this.activeFilters = activeFilters;
    }

    @Override
    public void visit(PokemonsContract.View screen) {
        screen.setProgressVisibility(isLoading);
        screen.setItems(list);
        for (String filter : availableFilters) {
            screen.updateFilterButtons(activeFilters.contains(filter), filter);
        }
    }

    public List<String> getActiveFilters() {
        return new ArrayList<>(activeFilters);
    }

    public static class SetItems extends PokemonsScreenState {

        public SetItems(List<PokemonFullDataSchema> items, List<String> availableFilters) {
            super(items, false, availableFilters, new ArrayList<>(0));
        }

        @Override
        public PokemonsScreenState merge(PokemonsScreenState prevState) {
            return new PokemonsScreenState(list, false, availableFilters, prevState.activeFilters);
        }
    }

    public static class AddItems extends PokemonsScreenState {

        public AddItems(List<PokemonFullDataSchema> items) {
            super(items, false, new ArrayList<>(0), new ArrayList<>(0));
        }

        @Override
        public PokemonsScreenState merge(PokemonsScreenState prevState) {
            List<PokemonFullDataSchema> list1 = new ArrayList<>(prevState.list.size() + list.size());
            list1.addAll(prevState.list);
            list1.addAll(list);
            return new PokemonsScreenState(list1, false, prevState.availableFilters, prevState.activeFilters);
        }
    }

    public static class SetProgressVisibility extends PokemonsScreenState {

        public SetProgressVisibility(boolean isLoading) {
            super(new ArrayList<>(0), isLoading, new ArrayList<>(0), new ArrayList<>(0));
        }

        @Override
        public PokemonsScreenState merge(PokemonsScreenState prevState) {
            return new PokemonsScreenState(prevState.list, isLoading, prevState.availableFilters, prevState.activeFilters);
        }
    }

    public static class ChangeFilterState extends PokemonsScreenState {

        public ChangeFilterState(List<String> filters) {
            super(new ArrayList<>(0), false, new ArrayList<>(0), filters);
        }

        @Override
        public PokemonsScreenState merge(PokemonsScreenState prevState) {
            return new PokemonsScreenState(prevState.list, prevState.isLoading, prevState.availableFilters, activeFilters);
        }
    }
}
