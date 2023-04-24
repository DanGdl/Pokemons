package com.mdgd.pokemon.models.filters;

import java.util.ArrayList;
import java.util.List;

public class FilterData {
    public static final String FILTER_ATTACK = "attack";
    public static final String FILTER_DEFENCE = "defense";
    public static final String FILTER_SPEED = "speed";

    private final List<String> filters;

    public FilterData() {
        this.filters = new ArrayList<>(0);
    }

    public FilterData(List<String> filters) {
        this.filters = filters;
    }

    public List<String> getFilters() {
        return filters;
    }

    public boolean isEmpty() {
        return filters.isEmpty();
    }
}
