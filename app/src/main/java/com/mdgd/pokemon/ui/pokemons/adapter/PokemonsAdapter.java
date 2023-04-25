package com.mdgd.pokemon.ui.pokemons.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.adapter.AbstractListAdapter;
import com.mdgd.pokemon.adapter.AbstractVH;
import com.mdgd.pokemon.adapter.ViewHolderFactory;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsAdapter extends AbstractListAdapter<Pokemon, PokemonsVhParams, PokemonsEvent> {

    public PokemonsAdapter() {
        super(new DiffUtil.ItemCallback<Pokemon>() {
            @Override
            public boolean areItemsTheSame(@NonNull Pokemon oldItem, @NonNull Pokemon newItem) {
                return oldItem == newItem;
            }

            @Override
            @SuppressLint("DiffUtilEquals")
            public boolean areContentsTheSame(@NonNull Pokemon oldItem, @NonNull Pokemon newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @Override
    protected Map<Integer, ViewHolderFactory<PokemonsVhParams>> createViewHolderFactories() {
        final Map<Integer, ViewHolderFactory<PokemonsVhParams>> factories = new HashMap<>();
        factories.put(0, params -> new PokemonViewHolder(inflate(R.layout.item_pokemon, params.parent), params.evensSubject));
        return factories;
    }

    @Override
    protected PokemonsVhParams getViewHolderParams(ViewGroup parent, int viewType) {
        return new PokemonsVhParams(parent, evensSubject);
    }

    public static class PokemonViewHolder extends AbstractVH<Pokemon> implements View.OnClickListener {

        private final PublishSubject<PokemonsEvent> clicksSubject;
        private final ImageView image;
        private final TextView name;
        private final TextView attack;
        private final TextView defence;
        private final TextView speed;

        public PokemonViewHolder(@NonNull View itemView, PublishSubject<PokemonsEvent> clicksSubject) {
            super(itemView);
            this.clicksSubject = clicksSubject;

            image = itemView.findViewById(R.id.item_pokemon_image);
            name = itemView.findViewById(R.id.item_pokemon_name);
            attack = itemView.findViewById(R.id.item_pokemon_attack);
            defence = itemView.findViewById(R.id.item_pokemon_defence);
            speed = itemView.findViewById(R.id.item_pokemon_speed);
        }

        @Override
        public void bind(Pokemon item) {
            final PokemonFullDataSchema pokemon = item.schema;
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
            clicksSubject.onNext(new PokemonsEvent(model.schema));
        }
    }
}
