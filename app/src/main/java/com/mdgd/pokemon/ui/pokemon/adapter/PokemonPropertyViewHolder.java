package com.mdgd.pokemon.ui.pokemon.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.pokemon.ui.pokemon.items.PokemonProperty;

public abstract class PokemonPropertyViewHolder<T extends PokemonProperty> extends RecyclerView.ViewHolder {

    public PokemonPropertyViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public void setupSubscriptions() {
    }

    public void clearSubscriptions() {
    }

    public abstract void bind(T property, int position);
}
