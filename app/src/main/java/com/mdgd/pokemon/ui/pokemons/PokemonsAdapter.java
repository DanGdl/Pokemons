package com.mdgd.pokemon.ui.pokemons;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;
import com.squareup.picasso.Picasso;

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

    public void updateItems(List<PokemonDetails> list) {
        final List<PokemonDetails> oldList = new ArrayList<>(items);

        this.items.clear();
        this.items.addAll(list);

        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return oldList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition) == list.get(newItemPosition);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition) == list.get(newItemPosition);
            }
        }).dispatchUpdatesTo(this);
    }

    public void addItems(List<PokemonDetails> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public Observable<PokemonDetails> getOnItemClickSubject() {
        return clicksSubject;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull PokemonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.setupSubscriptions();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PokemonViewHolder holder) {
        holder.clearSubscriptions();
        super.onViewDetachedFromWindow(holder);
    }


    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final PublishSubject<PokemonDetails> clicksSubject;
        private final ImageView image;
        private final TextView name;
        private final TextView attack;
        private final TextView defence;
        private final TextView speed;

        public PokemonViewHolder(@NonNull View itemView, PublishSubject<PokemonDetails> clicksSubject) {
            super(itemView);
            this.clicksSubject = clicksSubject;

            image = itemView.findViewById(R.id.item_pokemon_image);
            name = itemView.findViewById(R.id.item_pokemon_name);
            attack = itemView.findViewById(R.id.item_pokemon_attack);
            defence = itemView.findViewById(R.id.item_pokemon_defence);
            speed = itemView.findViewById(R.id.item_pokemon_speed);
        }

        public void bindItem(PokemonDetails pokemon, int position) {
            Picasso.get().load(pokemon.getImageUrl()).into(image);

            name.setText(pokemon.getName());
            final Resources resources = itemView.getContext().getResources();
            attack.setText(resources.getString(R.string.item_pokemon_attack, pokemon.getAttack()));
            defence.setText(resources.getString(R.string.item_pokemon_defence, pokemon.getDefence()));
            speed.setText(resources.getString(R.string.item_pokemon_speed, pokemon.getSpeed()));
        }

        @Override
        public void onClick(View view) {
            clicksSubject.onNext(items.get(getAdapterPosition()));
        }


        public void setupSubscriptions() {
            itemView.setOnClickListener(this);
        }

        public void clearSubscriptions() {
            itemView.setOnClickListener(null);
        }
    }
}
