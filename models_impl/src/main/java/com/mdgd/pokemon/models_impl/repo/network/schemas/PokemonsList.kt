package com.mdgd.pokemon.models_impl.repo.network.schemas

class PokemonsList {
    var count = 0
    var next: String? = null
    var previous: String? = null
    var results: MutableList<PokemonData>? = null
}
