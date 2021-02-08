package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder
import com.mdgd.pokemon.ui.pokemon.infra.ImageProperty
import com.squareup.picasso.Picasso

class PokemonImageViewHolder(view: View) : PokemonPropertyViewHolder<ImageProperty>(view) {
    private val image: ImageView = view.findViewById(R.id.pokemon_details_image)

    override fun bind(property: ImageProperty, position: Int) {
        if (!TextUtils.isEmpty(property.imageUrl)) {
            Picasso.get().load(property.imageUrl).into(image)
        }
    }
}
