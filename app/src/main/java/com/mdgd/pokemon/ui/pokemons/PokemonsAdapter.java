package com.mdgd.pokemon.ui.pokemons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.dto.Pokemon;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder> {
    private final List<Pokemon> items = new ArrayList<>();
    private final PublishSubject<Pokemon> clicksSubject = PublishSubject.create();

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false), clicksSubject);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.bindItem(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Pokemon> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Pokemon> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public Observable<Pokemon> getOnItemClickSubject() {
        return clicksSubject;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private final PublishSubject<Pokemon> clicksSubject;

        public PokemonViewHolder(@NonNull View itemView, PublishSubject<Pokemon> clicksSubject) {
            super(itemView);
            this.clicksSubject = clicksSubject;
        }

        public void bindItem(Pokemon pokemon, int position) {

        }
    }
}
