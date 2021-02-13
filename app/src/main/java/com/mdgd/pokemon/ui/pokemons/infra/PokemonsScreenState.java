package com.mdgd.pokemon.ui.pokemons.infra;

import com.mdgd.mvi.ScreenState;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;

import java.util.ArrayList;
import java.util.List;

public class PokemonsScreenState extends ScreenState<PokemonsContract.View> {

    private static final int SET_DATA = 1;
    private static final int ERROR = 3;
    private static final int LOADING = 4;
    private static final int UPDATE_DATA = 5;
    private static final int SHOW_DETAILS = 6;

    private final List<PokemonFullDataSchema> list;
    private final int action;
    private final Throwable error;
    private final long id;

    public PokemonsScreenState(int action, List<PokemonFullDataSchema> list) {
        this.action = action;
        this.list = list;
        this.error = null;
        id = -1;
    }

    public PokemonsScreenState(int action, Throwable error, List<PokemonFullDataSchema> list) {
        this.action = action;
        this.list = list;
        this.error = error;
        id = -1;
    }

    public PokemonsScreenState(int action, long id, List<PokemonFullDataSchema> list) {
        this.action = action;
        this.id = id;
        this.list = list;
        this.error = null;
    }

    public static PokemonsScreenState createSetDataState(List<PokemonFullDataSchema> list) {
        return new PokemonsScreenState(SET_DATA, list);
    }

    public static PokemonsScreenState createUpdateDataState(List<PokemonFullDataSchema> list) {
        return new PokemonsScreenState(UPDATE_DATA, list);
    }

    public static PokemonsScreenState createAddDataState(List<PokemonFullDataSchema> list, PokemonsScreenState lastState) {
        final List<PokemonFullDataSchema> data = new ArrayList<>(list.size() + lastState.list.size());
        data.addAll(lastState.list);
        data.addAll(list);
        return new PokemonsScreenState(SET_DATA, data);
    }

    public static PokemonsScreenState createErrorState(Throwable error, PokemonsScreenState lastState) {
        return new PokemonsScreenState(ERROR, error, lastState.list);
    }

    public static PokemonsScreenState createLoadingState(PokemonsScreenState lastState) {
        return new PokemonsScreenState(LOADING, lastState.list);
    }

    public static PokemonsScreenState createShowDetails(long id, PokemonsScreenState lastState) {
        return new PokemonsScreenState(SHOW_DETAILS, id, lastState.list);
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
        } else if (UPDATE_DATA == action) {
            pokemonsScreen.updateItems(list);
        } else if (ERROR == action) {
            pokemonsScreen.showError(error);
        } else if (SHOW_DETAILS == action) {
            pokemonsScreen.proceedToNextScreen(id);
        }
    }

    public boolean isError() {
        return ERROR == action;
    }
}
