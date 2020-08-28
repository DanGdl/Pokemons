package com.mdgd.pokemon.ui.pokemons.infra;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;
import com.mdgd.pokemon.ui.arch.Screen;

import java.util.List;

public interface PokemonsScreen extends Screen {
    void showProgress();

    void hideProgress();

    void setItems(List<PokemonDetails> list);

    void addItems(List<PokemonDetails> list);

    void updateItems(List<PokemonDetails> list);

    void showError(Throwable error);
}
