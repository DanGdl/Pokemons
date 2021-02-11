package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CacheImpl implements Cache {
    private final BehaviorSubject<Optional<PokemonFullDataSchema>> pokemon = BehaviorSubject.createDefault(Optional.absent());
    private final BehaviorSubject<Result<Long>> progress = BehaviorSubject.createDefault(new Result<>(0L));

    @Override
    public void putSelected(PokemonFullDataSchema pokemon) {
        this.pokemon.onNext(Optional.of(pokemon));
    }

    @Override
    public Optional<PokemonFullDataSchema> getSelectedPokemon() {
        return pokemon.getValue();
    }

    @Override
    public Observable<Optional<PokemonFullDataSchema>> getSelectedPokemonObservable() {
        return pokemon.hide();
    }

    @Override
    public void setLoadingProgress(Result<Long> value) {
        progress.onNext(value);
    }

    @Override
    public Observable<Result<Long>> getProgressObservable() {
        return progress;
    }
}
