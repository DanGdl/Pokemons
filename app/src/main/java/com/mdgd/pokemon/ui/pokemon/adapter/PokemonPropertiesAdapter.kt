package com.mdgd.pokemon.ui.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.adapter.RecyclerAdapter
import com.mdgd.pokemon.ui.adapter.RecyclerVH
import com.mdgd.pokemon.ui.pokemon.adapter.holders.*
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty
import java.util.*

class PokemonPropertiesAdapter : RecyclerAdapter<PokemonProperty>() {
    private val resolver: MutableMap<Int, ViewHolderFactory>

    init {
        resolver = HashMap()
        resolver[PokemonProperty.PROPERTY_IMAGE] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): RecyclerVH<out PokemonProperty> {
                return PokemonImageViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_image, parent, false))
            }
        }
        resolver[PokemonProperty.PROPERTY_LABEL] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): RecyclerVH<out PokemonProperty> {
                return PokemonLabelViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_label, parent, false))
            }
        }
        resolver[PokemonProperty.PROPERTY_TITLE] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): RecyclerVH<out PokemonProperty> {
                return PokemonTitleViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_title, parent, false))
            }
        }
        resolver[PokemonProperty.PROPERTY_TEXT] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): RecyclerVH<out PokemonProperty> {
                return PokemonTextViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_label_image, parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) {
            EMPTY_VIEW
        } else {
            items[position].type
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVH<PokemonProperty> {
        if (viewType == EMPTY_VIEW) {
            return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false))
        }
        return resolver[viewType]!!.create(parent) as RecyclerVH<PokemonProperty>
    }
}