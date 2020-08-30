package com.mdgd.pokemon.ui.pokemons.infra;

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.arch.ScreenState;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;

import java.util.LinkedList;
import java.util.List;

public class PokemonsScreenState extends ScreenState<PokemonsContract.View> {

    private static final int SET_DATA = 1;
    private static final int ADD_DATA = 2;
    private static final int ERROR = 3;
    private static final int LOADING = 4;
    private static final int UPDATE_DATA = 5;

    private final List<PokemonFullDataSchema> list;
    private final int action;
    private final Throwable error;

    public PokemonsScreenState(int action) {
        this.action = action;
        this.list = null;
        this.error = null;
    }

    public PokemonsScreenState(int action, List<PokemonFullDataSchema> list) {
        this.action = action;
        this.list = list;
        this.error = null;
    }

    public PokemonsScreenState(int action, Throwable error) {
        this.action = action;
        this.list = new LinkedList<>();
        this.error = error;
    }

    public static PokemonsScreenState createSetDataState(List<PokemonFullDataSchema> list) {
        return new PokemonsScreenState(SET_DATA, list);
    }

    public static PokemonsScreenState createUpdateDataState(List<PokemonFullDataSchema> list) {
        return new PokemonsScreenState(UPDATE_DATA, list);
    }

    public static PokemonsScreenState createAddDataState(List<PokemonFullDataSchema> list) {
        return new PokemonsScreenState(ADD_DATA, list);
    }

    public static PokemonsScreenState createErrorState(Throwable error) {
        return new PokemonsScreenState(ERROR, error);
    }

    public static PokemonsScreenState createLoadingState() {
        return new PokemonsScreenState(LOADING);
    }

    @Override
    public void visit(PokemonsContract.View pokemonsScreen) {
        if (LOADING == action) {
            pokemonsScreen.showProgress();
            return;
        }
        pokemonsScreen.hideProgress();
        if (SET_DATA == action) {
            pokemonsScreen.setItems(list);
        } else if (ADD_DATA == action) {
            pokemonsScreen.addItems(list);
        } else if (UPDATE_DATA == action) {
            pokemonsScreen.updateItems(list);
        } else if (ERROR == action) {
            pokemonsScreen.showError(error);
        }
    }

    public boolean isError() {
        return ERROR == action;
    }
}
