package com.mdgd.pokemon.ui.pokemons.dto;

import com.mdgd.pokemon.dto.PokemonDetails;

import java.util.LinkedList;
import java.util.List;

public class PokemonsScreenState {


    private static final int SET_DATA = 1;
    private static final int ADD_DATA = 2;
    private static final int ERROR = 3;
    private final List<PokemonDetails> list;
    private final int action;
    private final Throwable error;

    public PokemonsScreenState(int action, List<PokemonDetails> list) {
        this.action = action;
        this.list = list;
        this.error = null;
    }

    public PokemonsScreenState(int action, Throwable error) {
        this.action = action;
        this.list = new LinkedList<>();
        this.error = error;
    }

    public static PokemonsScreenState createSetDataState(List<PokemonDetails> list) {
        return new PokemonsScreenState(SET_DATA, list);
    }

    public static PokemonsScreenState createAddDataState(List<PokemonDetails> list) {
        return new PokemonsScreenState(ADD_DATA, list);
    }

    public static PokemonsScreenState createErrorState(Throwable error) {
        return new PokemonsScreenState(ERROR, error);
    }

    public void visit(PokemonsScreen pokemonsScreen) {
        pokemonsScreen.hideProgress();
        if (SET_DATA == action) {
            pokemonsScreen.setItems(list);
        } else if (ADD_DATA == action) {
            pokemonsScreen.addItems(list);
        } else if (ERROR == action) {
            pokemonsScreen.showError(error);
        }
    }
}
