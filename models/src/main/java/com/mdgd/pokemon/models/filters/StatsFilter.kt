package com.mdgd.pokemon.models.filters

interface StatsFilter {
    fun getAvailableFilters(): List<String>

    fun getFilters(): Map<String, CharacteristicComparator>
}
