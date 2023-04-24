package com.mdgd.pokemon.ui.pokemon.adapter.holders;

import android.view.View;
import android.widget.TextView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder;
import com.mdgd.pokemon.ui.pokemon.items.TextProperty;

public class PokemonTextViewHolder extends PokemonPropertyViewHolder<TextProperty> {

    private final TextView label;

    public PokemonTextViewHolder(View view) {
        super(view);
        label = view.findViewById(R.id.pokemon_details_text);
    }

    @Override
    public void bind(TextProperty property, int position) {
        label.setText(property.getText());

        label.setPaddingRelative(
                label.getResources().getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (2 + property.getNestingLevel()),
                label.getPaddingTop(), label.getPaddingEnd(), label.getPaddingBottom());
    }
}
