package com.mdgd.pokemon.models;

import com.mdgd.pokemon.ui.pokemon.PokemonDetailsViewModelFactory;
import com.mdgd.pokemon.ui.pokemons.PokemonsViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void injectPokemonsRepo(PokemonsViewModelFactory factory);

    void injectPokemonsCache(PokemonsViewModelFactory factory);

    void injectPokemonsCache(PokemonDetailsViewModelFactory factory);
}