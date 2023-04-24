package com.mdgd.pokemon.models.filters;

import java.util.List;
import java.util.Map;

public interface StatsFilter {
    List<String> getAvailableFilters();

    Map<String, CharacteristicComparator> getFilters();
}
