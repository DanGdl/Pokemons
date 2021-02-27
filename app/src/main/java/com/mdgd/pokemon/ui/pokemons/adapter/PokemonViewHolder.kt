package com.mdgd.pokemon.ui.pokemons.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.adapter.ClickEvent
import com.mdgd.pokemon.ui.adapter.RecyclerVH
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class PokemonViewHolder(itemView: View, private val clicksSubject: MutableStateFlow<ClickEvent<PokemonFullDataSchema>>,
                        private val lifecycleScope: LifecycleCoroutineScope)
    : RecyclerVH<PokemonFullDataSchema>(itemView), View.OnClickListener {

    private val image: ImageView = itemView.findViewById(R.id.item_pokemon_image)
    private val name: TextView = itemView.findViewById(R.id.item_pokemon_name)
    private val attack: TextView = itemView.findViewById(R.id.item_pokemon_attack)
    private val defence: TextView = itemView.findViewById(R.id.item_pokemon_defence)
    private val speed: TextView = itemView.findViewById(R.id.item_pokemon_speed)
    private var item: PokemonFullDataSchema? = null

    override fun bindItem(item: PokemonFullDataSchema, position: Int) {
        this.item = item
        val url = item.pokemonSchema!!.sprites!!.other!!.officialArtwork!!.frontDefault
        Picasso.get().load(url).into(image)
        name.text = item.pokemonSchema!!.name
        val resources = itemView.context.resources
        var attackVal = "--"
        for (s in item.stats) {
            if ("attack" == s.stat!!.name) {
                attackVal = s.baseStat.toString()
            }
        }
        attack.text = resources.getString(R.string.item_pokemon_attack, attackVal)
        var defenceVal = "--"
        for (s in item.stats) {
            if ("defense" == s.stat!!.name) {
                defenceVal = s.baseStat.toString()
            }
        }
        defence.text = resources.getString(R.string.item_pokemon_defence, defenceVal)
        var speedVal = "--"
        for (s in item.stats) {
            if ("speed" == s.stat!!.name) {
                speedVal = s.baseStat.toString()
            }
        }
        speed.text = resources.getString(R.string.item_pokemon_speed, speedVal)
    }

    override fun onClick(view: View) {
        if (item != null) {
            lifecycleScope.launch {
                clicksSubject.emit(ClickEvent.ClickData(item!!))
            }
        }
    }

    override fun setupSubscriptions() {
        itemView.setOnClickListener(this)
    }

    override fun clearSubscriptions() {
        itemView.setOnClickListener(null)
    }
}
