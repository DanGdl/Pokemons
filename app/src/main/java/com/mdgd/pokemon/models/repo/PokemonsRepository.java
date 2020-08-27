package com.mdgd.pokemon.models.repo;

import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.network.Network;

public class PokemonsRepository implements PokemonsRepo {
    private final PokemonsDao dao;
    private final Network network;

    public PokemonsRepository(PokemonsDao pokemonsDao, Network pokemonsNetwork) {
        this.dao = pokemonsDao;
        this.network = pokemonsNetwork;
    }
}
