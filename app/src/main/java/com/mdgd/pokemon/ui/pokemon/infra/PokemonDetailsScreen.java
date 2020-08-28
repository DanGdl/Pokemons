package com.mdgd.pokemon.ui.pokemon.infra;

import com.mdgd.pokemon.ui.arch.Screen;

import java.util.List;

public interface PokemonDetailsScreen extends Screen {

    void setItems(List<PokemonProperty> items);
}
