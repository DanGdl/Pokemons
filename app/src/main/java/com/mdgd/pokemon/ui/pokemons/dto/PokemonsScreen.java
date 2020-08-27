package com.mdgd.pokemon.ui.pokemons.dto;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

public interface PokemonsScreen {
    void showProgress();

    void hideProgress();

    void setItems(List<PokemonDetails> list);

    void addItems(List<PokemonDetails> list);

    void updateItems(List<PokemonDetails> list);

    void showError(Throwable error);
}
