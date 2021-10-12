package com.mdgd.pokemon.models.filters

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
        const val FILTER_DEFENCE = "defense"
        const val FILTER_SPEED = "speed"
    }
}
