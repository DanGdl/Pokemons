package com.mdgd.pokemon.ui.pokemon.adapter.holders;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder;
import com.mdgd.pokemon.ui.pokemon.infra.ImageProperty;
import com.squareup.picasso.Picasso;

public class PokemonImageViewHolder extends PokemonPropertyViewHolder<ImageProperty> {

    private final ImageView image;

    public PokemonImageViewHolder(View view) {
        super(view);
        image = view.findViewById(R.id.pokemon_details_image);
    }

    public void bind(ImageProperty property, int position) {
        if (!TextUtils.isEmpty(property.getImageUrl())) {
            Picasso.get().load(property.getImageUrl()).into(image);
        }
    }
}
