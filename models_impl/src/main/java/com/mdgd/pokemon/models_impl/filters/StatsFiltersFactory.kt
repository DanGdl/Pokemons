package com.mdgd.pokemon.models_impl.filters

import com.mdgd.pokemon.models.filters.CharacteristicComparator
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import java.util.*

class StatsFiltersFactory : StatsFilter {

    override fun getAvailableFilters(): List<String> {
        return listOf(FilterData.FILTER_ATTACK, FilterData.FILTER_DEFENCE, FilterData.FILTER_SPEED)
    }

    override fun getFilters(): Map<String, CharacteristicComparator> {
        return object : HashMap<String, CharacteristicComparator>() {
            init {
                put(FilterData.FILTER_ATTACK, object : CharacteristicComparator {
                    override fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
                        return compareProperty(FilterData.FILTER_ATTACK, p1, p2)
                    }
                })
                put(FilterData.FILTER_DEFENCE, object : CharacteristicComparator {
                    override fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
                        return compareProperty(FilterData.FILTER_DEFENCE, p1, p2)
                    }
                })
                put(FilterData.FILTER_SPEED, object : CharacteristicComparator {
                    override fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
                        return compareProperty(FilterData.FILTER_SPEED, p1, p2)
                    }
                })
            }
        }
    }

    private fun compareProperty(property: String, p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
        var val1 = -1
        for (s in p1.stats) {
            if (property == s.stat!!.name) {
                val1 = s.baseStat!!
                break
            }
        }
        var val2 = -1
        for (s in p2.stats) {
            if (property == s.stat!!.name) {
                val2 = s.baseStat!!
                break
            }
        }
        return val1.compareTo(val2)
    }
}