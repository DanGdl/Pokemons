package com.mdgd.pokemon.ui.pokemon;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;
import com.mdgd.pokemon.ui.arch.MviViewModel;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PokemonDetailsViewModel extends MviViewModel<PokemonDetailsScreenState> implements PokemonDetailsContract.ViewModel {

    private final Cache cache;

    public PokemonDetailsViewModel(Cache cache) {
        this.cache = cache;
    }

    @Override
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(cache.getSelectedPokemonObservable()
                    .map(optional -> optional.isPresent() ? mapToListPokemon(optional.get()) : new LinkedList<PokemonProperty>())
                    .subscribe(list -> setState(PokemonDetailsScreenState.createSetDataState(list))));
        }
    }

    private List<PokemonProperty> mapToListPokemon(PokemonDetails pokemonDetails) {
        final List<PokemonProperty> properties = new ArrayList<>();

        return properties;
    }
}
