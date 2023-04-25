package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.adapter.AbstractVH
import com.mdgd.pokemon.ui.pokemon.dto.TitleProperty

class PokemonTitleViewHolder(view: View) : AbstractVH<TitleProperty>(view) {
    private val title: TextView = view.findViewById(R.id.pokemon_property_title)

    override fun bind(item: TitleProperty) {
        if (item.titleResId != 0) {
            title.setText(item.titleResId)
        }
        if (item.nestingLevel == 0) {
            title.typeface = Typeface.DEFAULT_BOLD
            title.gravity = Gravity.CENTER
            title.setPaddingRelative(0, 0, 0, 0)
        } else {
            title.typeface = Typeface.SERIF
            title.gravity = Gravity.NO_GRAVITY
            title.setPaddingRelative(
                    title.resources.getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (2 + item.nestingLevel),
                    0, 0, 0)
        }
    }
}
