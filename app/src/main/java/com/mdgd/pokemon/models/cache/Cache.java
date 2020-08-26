package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.dto.Pokemon;

import io.reactivex.rxjava3.annotations.Nullable;

public interface Cache {
    void putSelected(Pokemon pokemon);

    @Nullable Optional<Pokemon> getSelectedPokemon();
}
