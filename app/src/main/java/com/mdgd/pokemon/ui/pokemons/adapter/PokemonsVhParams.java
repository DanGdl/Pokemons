package com.mdgd.pokemon.ui.pokemons.adapter;

import android.view.ViewGroup;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsVhParams {
    public final PublishSubject<PokemonsEvent> evensSubject;
    public final ViewGroup parent;

    public PokemonsVhParams(ViewGroup parent, PublishSubject<PokemonsEvent> evensSubject) {
        this.parent = parent;
        this.evensSubject = evensSubject;
    }
}
