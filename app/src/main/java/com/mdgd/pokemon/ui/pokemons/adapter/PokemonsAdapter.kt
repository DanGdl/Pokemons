package com.mdgd.pokemon.ui.pokemons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.adapter.RecyclerAdapter
import com.mdgd.pokemon.ui.adapter.RecyclerVH

class PokemonsAdapter(private val lifecycleScope: LifecycleCoroutineScope) : RecyclerAdapter<PokemonFullDataSchema>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVH<PokemonFullDataSchema> {
        if (viewType == EMPTY_VIEW) {
            return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false))
        }
        return PokemonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false), clicksSubject, lifecycleScope)
    }
}
