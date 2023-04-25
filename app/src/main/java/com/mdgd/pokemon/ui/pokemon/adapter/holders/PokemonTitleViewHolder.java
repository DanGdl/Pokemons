package com.mdgd.pokemon.ui.pokemon.adapter.holders;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.adapter.AbstractVH;
import com.mdgd.pokemon.ui.pokemon.items.TitleProperty;

public class PokemonTitleViewHolder extends AbstractVH<TitleProperty> {

    private final TextView title;

    public PokemonTitleViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.pokemon_property_title);
    }

    @Override
    public void bind(TitleProperty property) {
        if (property.getTitleResId() != 0) {
            title.setText(property.getTitleResId());
        }
        if (property.getNestingLevel() == 0) {
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setGravity(Gravity.CENTER);
            title.setPaddingRelative(0, 0, 0, 0);
        } else {
            title.setTypeface(Typeface.SERIF);
            title.setGravity(Gravity.NO_GRAVITY);
            title.setPaddingRelative(
                    title.getResources().getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (2 + property.getNestingLevel()),
                    0, 0, 0);
        }
    }
}
