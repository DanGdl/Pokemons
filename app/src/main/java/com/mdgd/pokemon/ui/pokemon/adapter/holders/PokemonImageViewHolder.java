package com.mdgd.pokemon.ui.pokemon.adapter.holders;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.adapter.AbstractVH;
import com.mdgd.pokemon.ui.pokemon.items.ImageProperty;
import com.squareup.picasso.Picasso;

public class PokemonImageViewHolder extends AbstractVH<ImageProperty> {

    private final ImageView image;

    public PokemonImageViewHolder(View view) {
        super(view);
        image = view.findViewById(R.id.pokemon_details_image);
    }

    @Override
    public void bind(ImageProperty property) {
        if (!TextUtils.isEmpty(property.getImageUrl())) {
            Picasso.get().load(property.getImageUrl()).into(image);
        }
    }
}
