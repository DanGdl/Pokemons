package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.view.View
import android.widget.TextView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder
import com.mdgd.pokemon.ui.pokemon.infra.LabelProperty

class PokemonLabelViewHolder(view: View) : PokemonPropertyViewHolder<LabelProperty>(view) {
    private val label: TextView = view.findViewById(R.id.pokemon_details_label_text)
    private val value: TextView = view.findViewById(R.id.pokemon_details_label_value)

    override fun bind(property: LabelProperty, position: Int) {
        label.setPaddingRelative(
                label.resources.getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (1 + property.nestingLevel),
                0, 0, 0)

        if (property.titleResId == 0) {
            label.text = property.titleStr
        } else {
            label.setText(property.titleResId)
        }
        value.text = property.text
    }
}
