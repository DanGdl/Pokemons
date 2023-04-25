package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.view.View
import android.widget.TextView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.adapter.AbstractVH
import com.mdgd.pokemon.ui.pokemon.dto.TextProperty

class PokemonTextViewHolder(view: View) : AbstractVH<TextProperty>(view) {
    private val label: TextView = view.findViewById(R.id.pokemon_details_text)

    override fun bind(item: TextProperty) {
        label.text = item.text
        label.setPaddingRelative(
            label.resources.getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (2 + item.nestingLevel),
            label.paddingTop, label.paddingEnd, label.paddingBottom
        )
    }
}
