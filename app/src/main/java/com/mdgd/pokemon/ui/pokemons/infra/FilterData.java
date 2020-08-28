package com.mdgd.pokemon.ui.pokemons.infra;

import java.util.LinkedList;
import java.util.List;

public class FilterData {
    public static final String FILTER_ATTACK = "attack";
    public static final String FILTER_DEFENCE = "defence";
    public static final String FILTER_SPEED = "speed";

    private final List<String> filters;


    public FilterData() {
        this.filters = new LinkedList<>();
    }

    public FilterData(List<String> filters) {
        this.filters = filters;
    }

    public boolean isEmpty() {
        return filters.isEmpty();
    }

    public List<String> getFilters() {
        return filters;
    }
}
