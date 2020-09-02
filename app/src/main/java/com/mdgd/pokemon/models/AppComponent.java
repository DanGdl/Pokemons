package com.mdgd.pokemon.models;

import com.mdgd.pokemon.bg.PokemonsLoadingModelFactory;
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsViewModelFactory;
import com.mdgd.pokemon.ui.pokemons.PokemonsViewModelFactory;
import com.mdgd.pokemon.ui.splash.SplashViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DefaultAppModule.class})
public interface AppComponent {
    void injectPokemonsRepo(PokemonsViewModelFactory factory);

    void injectPokemonsCache(PokemonsViewModelFactory factory);

    void injectPokemonsCache(PokemonDetailsViewModelFactory factory);

    void injectPokemonsRepo(PokemonsLoadingModelFactory factory);

    void injectPokemonsCache(PokemonsLoadingModelFactory factory);

    void injectPokemonsCache(SplashViewModelFactory factory);
}