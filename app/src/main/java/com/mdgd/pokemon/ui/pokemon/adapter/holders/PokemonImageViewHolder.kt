package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.adapter.AbstractVH
import com.mdgd.pokemon.ui.pokemon.dto.ImageProperty
import com.squareup.picasso.Picasso

class PokemonImageViewHolder(view: View) : AbstractVH<ImageProperty>(view) {
    private val image: ImageView = view.findViewById(R.id.pokemon_details_image)

    override fun bind(item: ImageProperty) {
        if (!TextUtils.isEmpty(item.imageUrl)) {
            Picasso.get().load(item.imageUrl).into(image)
        }
    }
}
