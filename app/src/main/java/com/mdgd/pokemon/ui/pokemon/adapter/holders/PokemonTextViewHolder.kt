package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.view.View
import android.widget.TextView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder
import com.mdgd.pokemon.ui.pokemon.infra.TextProperty

class PokemonTextViewHolder(view: View) : PokemonPropertyViewHolder<TextProperty>(view) {
    private val label: TextView = view.findViewById(R.id.pokemon_details_text)

    override fun bind(property: TextProperty, position: Int) {
        label.text = property.text
        label.setPaddingRelative(
                label.resources.getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (2 + property.nestingLevel),
                label.paddingTop, label.paddingEnd, label.paddingBottom)
    }
}
