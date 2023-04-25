package com.mdgd.pokemon.ui.pokemon.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.adapter.AbstractListAdapter;
import com.mdgd.pokemon.adapter.ViewHolderFactory;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonImageViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonLabelViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonTextViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonTitleViewHolder;
import com.mdgd.pokemon.ui.pokemon.items.PokemonProperty;

import java.util.HashMap;
import java.util.Map;

public class PokemonPropertiesAdapter extends AbstractListAdapter<PokemonProperty, ViewGroup, PokemonDetailsEvent> {

    public PokemonPropertiesAdapter() {
        super(new DiffUtil.ItemCallback<PokemonProperty>() {
            @Override
            public boolean areItemsTheSame(@NonNull PokemonProperty oldItem, @NonNull PokemonProperty newItem) {
                return oldItem == newItem;
            }

            @Override
            @SuppressLint("DiffUtilEquals")
            public boolean areContentsTheSame(@NonNull PokemonProperty oldItem, @NonNull PokemonProperty newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    protected Map<Integer, ViewHolderFactory<ViewGroup>> createViewHolderFactories() {
        final Map<Integer, ViewHolderFactory<ViewGroup>> factories = new HashMap<>();
        factories.put(PokemonProperty.PROPERTY_IMAGE, parent -> new PokemonImageViewHolder(inflate(R.layout.item_pokemon_image, parent)));
        factories.put(PokemonProperty.PROPERTY_LABEL, parent -> new PokemonLabelViewHolder(inflate(R.layout.item_pokemon_label, parent)));
        factories.put(PokemonProperty.PROPERTY_TITLE, parent -> new PokemonTitleViewHolder(inflate(R.layout.item_pokemon_title, parent)));
        factories.put(PokemonProperty.PROPERTY_TEXT, parent -> new PokemonTextViewHolder(inflate(R.layout.item_pokemon_label_image, parent)));
        return factories;
    }

    @Override
    protected ViewGroup getViewHolderParams(ViewGroup parent, int viewType) {
        return parent;
    }
}
