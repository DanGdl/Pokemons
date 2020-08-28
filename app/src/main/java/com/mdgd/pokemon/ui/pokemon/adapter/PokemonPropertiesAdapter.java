package com.mdgd.pokemon.ui.pokemon.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;

import java.util.ArrayList;
import java.util.List;

public class PokemonPropertiesAdapter extends RecyclerView.Adapter<PokemonPropertyViewHolder> {
    private final List<PokemonProperty> items = new ArrayList<>();

    @NonNull
    @Override
    public PokemonPropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null; // todo resolve
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
