package com.mdgd.pokemon.ui.pokemons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.dto.PokemonDetails;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder> {
    private final List<PokemonDetails> items = new ArrayList<>();
    private final PublishSubject<PokemonDetails> clicksSubject = PublishSubject.create();

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

    public void setItems(List<PokemonDetails> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<PokemonDetails> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public Observable<PokemonDetails> getOnItemClickSubject() {
        return clicksSubject;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private final PublishSubject<PokemonDetails> clicksSubject;

        public PokemonViewHolder(@NonNull View itemView, PublishSubject<PokemonDetails> clicksSubject) {
            super(itemView);
            this.clicksSubject = clicksSubject;
        }

        public void bindItem(PokemonDetails pokemon, int position) {

        }
    }
}
