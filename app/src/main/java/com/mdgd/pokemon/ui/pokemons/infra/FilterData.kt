package com.mdgd.pokemon.ui.pokemons.infra

import java.util.*

class FilterData {
    val filters: List<String>

    constructor() {
        filters = LinkedList()
    }

    constructor(filters: List<String>) {
        this.filters = filters
    }

    val isEmpty: Boolean
        get() = filters.isEmpty()

    companion object {
        const val FILTER_ATTACK = "attack"
        const val FILTER_DEFENCE = "defence"
        const val FILTER_SPEED = "speed"
    }
}
