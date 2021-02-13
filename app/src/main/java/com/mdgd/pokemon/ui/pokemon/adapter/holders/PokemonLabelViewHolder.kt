package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.view.View
import android.widget.TextView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.adapter.RecyclerVH
import com.mdgd.pokemon.ui.pokemon.infra.LabelProperty

class PokemonLabelViewHolder(view: View) : RecyclerVH<LabelProperty>(view) {
    private val label: TextView = view.findViewById(R.id.pokemon_details_label_text)
    private val value: TextView = view.findViewById(R.id.pokemon_details_label_value)

    override fun bindItem(item: LabelProperty, position: Int) {
        label.setPaddingRelative(
                label.resources.getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (1 + item.nestingLevel),
                0, 0, 0)

        if (item.titleResId == 0) {
            label.text = item.titleStr
        } else {
            label.setText(item.titleResId)
        }
        value.text = item.text
    }
}
