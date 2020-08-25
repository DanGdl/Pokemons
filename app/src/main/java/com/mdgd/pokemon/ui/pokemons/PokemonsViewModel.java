package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.pokemon.ui.arch.MviViewModel;

public class PokemonsViewModel extends MviViewModel<PokemonsScreenState> implements PokemonsContract.ViewModel {

    private final PokemonsContract.Router router;

    public PokemonsViewModel(PokemonsContract.Router router) {
        this.router = router;
    }
}
