package com.mdgd.pokemon.ui.pokemons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.PokemonsAdapter.PokemonViewHolder
import com.mdgd.pokemon.ui.pokemons.infra.ui.ClickEvent
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class PokemonsAdapter(private val lifecycleScope: LifecycleCoroutineScope) : RecyclerView.Adapter<PokemonViewHolder>() {
    private val items: MutableList<PokemonFullDataSchema> = ArrayList()
    private val clicksSubject = MutableStateFlow<ClickEvent<PokemonFullDataSchema>>(ClickEvent.EmptyData())

    val onItemClickSubject: Flow<ClickEvent<PokemonFullDataSchema>>
        get() = clicksSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        // todo: add empty view?
        return PokemonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false), clicksSubject)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindItem(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<PokemonFullDataSchema>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItems(list: List<PokemonFullDataSchema>) {
        val oldList: List<PokemonFullDataSchema> = ArrayList(items)
        items.clear()
        items.addAll(list)
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldList.size
            }

            override fun getNewListSize(): Int {
                return oldList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == list[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    fun addItems(items: List<PokemonFullDataSchema>?) {
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: PokemonViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setupSubscriptions()
    }

    override fun onViewDetachedFromWindow(holder: PokemonViewHolder) {
        holder.clearSubscriptions()
        super.onViewDetachedFromWindow(holder)
    }

    inner class PokemonViewHolder(itemView: View, private val clicksSubject: MutableStateFlow<ClickEvent<PokemonFullDataSchema>>)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.item_pokemon_image)
        private val name: TextView = itemView.findViewById(R.id.item_pokemon_name)
        private val attack: TextView = itemView.findViewById(R.id.item_pokemon_attack)
        private val defence: TextView = itemView.findViewById(R.id.item_pokemon_defence)
        private val speed: TextView = itemView.findViewById(R.id.item_pokemon_speed)

        fun bindItem(pokemon: PokemonFullDataSchema, position: Int) {
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
            lifecycleScope.launch {
                clicksSubject.emit(ClickEvent.ClickData(items[adapterPosition]))
            }
        }

        fun setupSubscriptions() {
            itemView.setOnClickListener(this)
        }

        fun clearSubscriptions() {
            itemView.setOnClickListener(null)
        }
    }
}
