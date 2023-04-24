package com.mdgd.pokemon.models_impl.filters;

import com.mdgd.pokemon.models.filters.CharacteristicComparator;
import com.mdgd.pokemon.models.filters.FilterData;
import com.mdgd.pokemon.models.filters.StatsFilter;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.schemas.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsFiltersFactory implements StatsFilter {

    @Override
    public List<String> getAvailableFilters() {
        return new ArrayList<>(Arrays.asList(FilterData.FILTER_ATTACK, FilterData.FILTER_DEFENCE, FilterData.FILTER_SPEED));
    }

    @Override
    public Map<String, CharacteristicComparator> getFilters() {
        return new HashMap<String, CharacteristicComparator>() {{
            put(FilterData.FILTER_ATTACK, (p1, p2) -> compareProperty(FilterData.FILTER_ATTACK, p1, p2));
            put(FilterData.FILTER_DEFENCE, (p1, p2) -> compareProperty(FilterData.FILTER_DEFENCE, p1, p2));
            put(FilterData.FILTER_SPEED, (p1, p2) -> compareProperty(FilterData.FILTER_SPEED, p1, p2));
        }};
    }

    private int compareProperty(String property, PokemonFullDataSchema p1, PokemonFullDataSchema p2) {
        int val1 = -1;
        for (Stat s : p1.getStats()) {
            if (s.getStat() != null && property.equals(s.getStat().getName())) {
                val1 = s.getBaseStat();
                break;
            }
        }
        int val2 = -1;
        for (Stat s : p2.getStats()) {
            if (s.getStat() != null && property.equals(s.getStat().getName())) {
                val2 = s.getBaseStat();
                break;
            }
        }
        return Integer.compare(val1, val2);
    }
}
