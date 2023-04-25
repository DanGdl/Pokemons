package com.mdgd.pokemon.ui.pokemons.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.adapter.AbstractVH
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.MutableSharedFlow

class PokemonViewHolder(
    itemView: View,
    private val clicksSubject: MutableSharedFlow<PokemonsEvent>
) : AbstractVH<Pokemon>(itemView), View.OnClickListener {

    private val image: ImageView = itemView.findViewById(R.id.item_pokemon_image)
    private val name: TextView = itemView.findViewById(R.id.item_pokemon_name)
    private val attack: TextView = itemView.findViewById(R.id.item_pokemon_attack)
    private val defence: TextView = itemView.findViewById(R.id.item_pokemon_defence)
    private val speed: TextView = itemView.findViewById(R.id.item_pokemon_speed)

    override fun bind(item: Pokemon) {
        val pokemon = item.schema

        val url = pokemon.pokemonSchema!!.sprites!!.other!!.officialArtwork!!.frontDefault
        Picasso.get().load(url).into(image)
        name.text = pokemon.pokemonSchema!!.name
        val resources = itemView.context.resources
        var attackVal = "--"
        for (s in pokemon.stats) {
            if ("attack" == s.stat!!.name) {
                attackVal = s.baseStat.toString()
            }
        }
        attack.text = resources.getString(R.string.item_pokemon_attack, attackVal)
        var defenceVal = "--"
        for (s in pokemon.stats) {
            if ("defense" == s.stat!!.name) {
                defenceVal = s.baseStat.toString()
            }
        }
        defence.text = resources.getString(R.string.item_pokemon_defence, defenceVal)
        var speedVal = "--"
        for (s in pokemon.stats) {
            if ("speed" == s.stat!!.name) {
                speedVal = s.baseStat.toString()
            }
        }
        speed.text = resources.getString(R.string.item_pokemon_speed, speedVal)
    }

    override fun onClick(view: View) {
        model?.let { clicksSubject.tryEmit(PokemonsEvent(it.schema)) }
    }
}
