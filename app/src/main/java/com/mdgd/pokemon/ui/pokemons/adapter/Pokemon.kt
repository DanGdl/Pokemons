package com.mdgd.pokemon.ui.pokemons.adapter

import com.mdgd.pokemon.adapter.ViewHolderDataItem
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

class Pokemon(val schema: PokemonFullDataSchema) : ViewHolderDataItem {
    override fun getViewHolderType(position: Int): Int = 0
}
