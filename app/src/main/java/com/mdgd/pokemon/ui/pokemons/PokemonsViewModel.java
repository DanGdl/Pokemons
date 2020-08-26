package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.pokemon.dto.Pokemon;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.ui.arch.MviViewModel;
import com.mdgd.pokemon.ui.pokemons.dto.FilterData;
import com.mdgd.pokemon.ui.pokemons.dto.PokemonsScreenState;

public class PokemonsViewModel extends MviViewModel<PokemonsScreenState> implements PokemonsContract.ViewModel {

    private final PokemonsContract.Router router;
    private final PokemonsRepo repo;
    private final Cache cache;

    public PokemonsViewModel(PokemonsContract.Router router, PokemonsRepo repo, Cache cache) {
        this.router = router;
        this.repo = repo;
        this.cache = cache;
    }

    @Override
    public void reload() {
        // todo request from repo
    }

    @Override
    public void sort(FilterData filterData) {
        // todo sort cached list
    }

    @Override
    public void onItemClicked(Pokemon pokemon) {
        cache.putSelected(pokemon);
        router.proceedToNextScreen();
    }
}
