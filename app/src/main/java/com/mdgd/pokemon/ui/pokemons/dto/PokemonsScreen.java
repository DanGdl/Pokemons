package com.mdgd.pokemon.ui.pokemons.dto;

import com.mdgd.pokemon.dto.PokemonDetails;

import java.util.List;

public interface PokemonsScreen {
    void hideProgress();

    void setItems(List<PokemonDetails> list);

    void addItems(List<PokemonDetails> list);

    void showError(Throwable error);
}
