package com.mdgd.pokemon.ui.pokemon.adapter.holders;

import android.view.View;
import android.widget.TextView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder;
import com.mdgd.pokemon.ui.pokemon.infra.LabelProperty;

public class PokemonLabelViewHolder extends PokemonPropertyViewHolder<LabelProperty> {

    private final TextView label;
    private final TextView value;

    public PokemonLabelViewHolder(View view) {
        super(view);
        label = view.findViewById(R.id.pokemon_details_label_text);
        value = view.findViewById(R.id.pokemon_details_label_value);
    }

    @Override
    public void bind(LabelProperty property, int position) {
        label.setPaddingRelative(
                label.getResources().getDimensionPixelSize(R.dimen.pokemon_details_nesting_level_padding) * (1 + property.getNestingLevel()),
                0, 0, 0);
        if (property.getTitleResId() != 0) {
            label.setText(property.getTitleResId());
        } else {
            label.setText(property.getTitleStr());
        }
        value.setText(property.getText());
    }
}
