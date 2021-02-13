package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.adapter.RecyclerVH
import com.mdgd.pokemon.ui.pokemon.infra.ImageProperty
import com.squareup.picasso.Picasso

class PokemonImageViewHolder(view: View) : RecyclerVH<ImageProperty>(view) {
    private val image: ImageView = view.findViewById(R.id.pokemon_details_image)

    override fun bindItem(item: ImageProperty, position: Int) {
        if (!TextUtils.isEmpty(item.imageUrl)) {
            Picasso.get().load(item.imageUrl).into(image)
        }
    }
}
