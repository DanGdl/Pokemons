package com.mdgd.pokemon.ui.pokemon.infra;

import com.mdgd.pokemon.ui.arch.ScreenState;

import java.util.List;

public class PokemonDetailsScreenState extends ScreenState<PokemonDetailsScreen> {
    private static final int ACTION_SET = 1;

    private final List<PokemonProperty> items;
    private final int action;

    public static PokemonDetailsScreenState createSetDataState(List<PokemonProperty> properties) {
        return new PokemonDetailsScreenState(ACTION_SET, properties);
    }

    public PokemonDetailsScreenState(int action, List<PokemonProperty> items) {
        this.action = action;
        this.items = items;
    }

    public void visit(PokemonDetailsScreen screen) {
        if (action == ACTION_SET) {
            screen.setItems(items);
        }
    }
}
