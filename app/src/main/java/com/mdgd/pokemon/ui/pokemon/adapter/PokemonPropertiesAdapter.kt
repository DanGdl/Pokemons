package com.mdgd.pokemon.ui.pokemon.adapter

import android.view.ViewGroup
import com.mdgd.pokemon.R
import com.mdgd.pokemon.adapter.AbstractListAdapter
import com.mdgd.pokemon.adapter.AbstractVH
import com.mdgd.pokemon.adapter.ViewHolderFactory
import com.mdgd.pokemon.ui.pokemon.adapter.holders.EmptyViewHolder
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonImageViewHolder
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonLabelViewHolder
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonTextViewHolder
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonTitleViewHolder
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty

class PokemonPropertiesAdapter :
    AbstractListAdapter<PokemonProperty, ViewGroup, PokemonDetailsEvent>(diffCallback = PokemonPropertiesDiffCallback()) {

    override fun createViewHolderFactories(): Map<Int, ViewHolderFactory<ViewGroup>> = hashMapOf(
        PokemonProperty.EMPTY_VIEW to object : ViewHolderFactory<ViewGroup> {
            override fun createViewHolder(params: ViewGroup): AbstractVH<*> {
                return EmptyViewHolder(inflate(R.layout.item_empty, params))
            }
        },
        PokemonProperty.PROPERTY_IMAGE to object : ViewHolderFactory<ViewGroup> {
            override fun createViewHolder(params: ViewGroup): AbstractVH<*> {
                return PokemonImageViewHolder(inflate(R.layout.item_pokemon_image, params))
            }
        },
        PokemonProperty.PROPERTY_LABEL to object : ViewHolderFactory<ViewGroup> {
            override fun createViewHolder(params: ViewGroup): AbstractVH<*> {
                return PokemonLabelViewHolder(inflate(R.layout.item_pokemon_label, params))
            }
        },
        PokemonProperty.PROPERTY_TITLE to object : ViewHolderFactory<ViewGroup> {
            override fun createViewHolder(params: ViewGroup): AbstractVH<*> {
                return PokemonTitleViewHolder(inflate(R.layout.item_pokemon_title, params))
            }
        },
        PokemonProperty.PROPERTY_TEXT to object : ViewHolderFactory<ViewGroup> {
            override fun createViewHolder(params: ViewGroup): AbstractVH<*> {
                return PokemonTextViewHolder(inflate(R.layout.item_pokemon_label_image, params))
            }
        }
    )

    override fun getViewHolderParams(parent: ViewGroup, viewType: Int): ViewGroup = parent
}
