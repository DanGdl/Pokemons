package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.dto.Pokemon;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CacheImpl implements Cache {
    private BehaviorSubject<Optional<Pokemon>> pokemon = BehaviorSubject.createDefault(Optional.absent());

    @Override
    public void putSelected(Pokemon pokemon) {
        this.pokemon.onNext(Optional.of(pokemon));
    }

    @Override
    public Optional<Pokemon> getSelectedPokemon() {
        return pokemon.getValue();
    }
}
