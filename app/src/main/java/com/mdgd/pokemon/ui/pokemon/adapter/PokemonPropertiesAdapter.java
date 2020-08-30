package com.mdgd.pokemon.ui.pokemon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonImageViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonLabelImageViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonLabelViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.PokemonTitleViewHolder;
import com.mdgd.pokemon.ui.pokemon.adapter.holders.ViewHolderFactory;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonPropertiesAdapter extends RecyclerView.Adapter<PokemonPropertyViewHolder<PokemonProperty>> {
    private final List<PokemonProperty> items = new ArrayList<>();
    private final Map<Integer, ViewHolderFactory> resolver;

    public PokemonPropertiesAdapter() {
        resolver = new HashMap<>();
        resolver.put(PokemonProperty.PROPERTY_IMAGE, parent -> new PokemonImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_image, parent, false)));
        resolver.put(PokemonProperty.PROPERTY_LABEL, parent -> new PokemonLabelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_label, parent, false)));
        resolver.put(PokemonProperty.PROPERTY_TITLE, parent -> new PokemonTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_title, parent, false)));
        resolver.put(PokemonProperty.PROPERTY_LABEL_IMAGE, parent -> new PokemonLabelImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_label_image, parent, false)));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public PokemonPropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return resolver.get(viewType).create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonPropertyViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull PokemonPropertyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.setupSubscriptions();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PokemonPropertyViewHolder holder) {
        holder.clearSubscriptions();
        super.onViewDetachedFromWindow(holder);
    }

    public void setItems(List<PokemonProperty> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
