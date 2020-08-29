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
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder> {
    private final List<PokemonFullDataSchema> items = new ArrayList<>();
    private final PublishSubject<PokemonFullDataSchema> clicksSubject = PublishSubject.create();

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

    public void setItems(List<PokemonFullDataSchema> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void updateItems(List<PokemonFullDataSchema> list) {
        final List<PokemonFullDataSchema> oldList = new ArrayList<>(items);

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

    public void addItems(List<PokemonFullDataSchema> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public Observable<PokemonFullDataSchema> getOnItemClickSubject() {
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

        private final PublishSubject<PokemonFullDataSchema> clicksSubject;
        private final ImageView image;
        private final TextView name;
        private final TextView attack;
        private final TextView defence;
        private final TextView speed;

        public PokemonViewHolder(@NonNull View itemView, PublishSubject<PokemonFullDataSchema> clicksSubject) {
            super(itemView);
            this.clicksSubject = clicksSubject;

            image = itemView.findViewById(R.id.item_pokemon_image);
            name = itemView.findViewById(R.id.item_pokemon_name);
            attack = itemView.findViewById(R.id.item_pokemon_attack);
            defence = itemView.findViewById(R.id.item_pokemon_defence);
            speed = itemView.findViewById(R.id.item_pokemon_speed);
        }

        public void bindItem(PokemonFullDataSchema pokemon, int position) {
            final String url = pokemon.getPokemonSchema().getSprites().getOther().getOfficialArtwork().getFrontDefault();
            Picasso.get().load(url).into(image);

            name.setText(pokemon.getPokemonSchema().getName());
            final Resources resources = itemView.getContext().getResources();

            String attackVal = "--";
            for (Stat s : pokemon.getStats()) {
                if ("attack".equals(s.getStat().getName())) {
                    attackVal = String.valueOf(s.getBaseStat());
                }
            }
            attack.setText(resources.getString(R.string.item_pokemon_attack, attackVal));

            String defenceVal = "--";
            for (Stat s : pokemon.getStats()) {
                if ("defense".equals(s.getStat().getName())) {
                    defenceVal = String.valueOf(s.getBaseStat());
                }
            }
            defence.setText(resources.getString(R.string.item_pokemon_defence, defenceVal));

            String speedVal = "--";
            for (Stat s : pokemon.getStats()) {
                if ("speed".equals(s.getStat().getName())) {
                    speedVal = String.valueOf(s.getBaseStat());
                }
            }
            speed.setText(resources.getString(R.string.item_pokemon_speed, speedVal));
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
