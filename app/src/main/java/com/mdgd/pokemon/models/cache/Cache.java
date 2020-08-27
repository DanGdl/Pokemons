package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.dto.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;

public interface Cache {
    void putSelected(PokemonDetails pokemon);

    @Nullable Optional<PokemonDetails> getSelectedPokemon();

    void setPokemons(List<PokemonDetails> list);

    void addPokemons(List<PokemonDetails> list);

    List<PokemonDetails> getPokemons();
}
