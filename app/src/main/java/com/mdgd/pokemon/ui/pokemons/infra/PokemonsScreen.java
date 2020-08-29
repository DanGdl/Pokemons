package com.mdgd.pokemon.ui.pokemons.infra;

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.arch.Screen;

import java.util.List;

public interface PokemonsScreen extends Screen {
    void showProgress();

    void hideProgress();

    void setItems(List<PokemonFullDataSchema> list);

    void addItems(List<PokemonFullDataSchema> list);

    void updateItems(List<PokemonFullDataSchema> list);

    void showError(Throwable error);
}
