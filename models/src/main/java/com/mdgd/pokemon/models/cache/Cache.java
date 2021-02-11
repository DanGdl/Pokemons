package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

public interface Cache {
    void putSelected(PokemonFullDataSchema pokemon);

    @Nullable Optional<PokemonFullDataSchema> getSelectedPokemon();

    Observable<Optional<PokemonFullDataSchema>> getSelectedPokemonObservable();

    void setLoadingProgress(Result<Long> value);

    Observable<Result<Long>> getProgressObservable();
}
