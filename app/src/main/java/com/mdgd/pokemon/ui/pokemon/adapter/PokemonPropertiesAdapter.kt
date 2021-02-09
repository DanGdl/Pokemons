package com.mdgd.pokemon.ui.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.pokemon.adapter.holders.*
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty
import java.util.*

class PokemonPropertiesAdapter : RecyclerView.Adapter<PokemonPropertyViewHolder<PokemonProperty>>() {
    private val items: MutableList<PokemonProperty> = ArrayList()
    private val resolver: MutableMap<Int, ViewHolderFactory>

    init {
        resolver = HashMap()
        resolver[PokemonProperty.PROPERTY_IMAGE] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): PokemonPropertyViewHolder<out PokemonProperty> {
                return PokemonImageViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_image, parent, false))
            }
        }
        resolver[PokemonProperty.PROPERTY_LABEL] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): PokemonPropertyViewHolder<out PokemonProperty> {
                return PokemonLabelViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_label, parent, false))
            }
        }
        resolver[PokemonProperty.PROPERTY_TITLE] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): PokemonPropertyViewHolder<out PokemonProperty> {
                return PokemonTitleViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_title, parent, false))
            }
        }
        resolver[PokemonProperty.PROPERTY_TEXT] = object : ViewHolderFactory {
            override fun create(parent: ViewGroup?): PokemonPropertyViewHolder<out PokemonProperty> {
                return PokemonTextViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon_label_image, parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonPropertyViewHolder<PokemonProperty> {
        return resolver[viewType]!!.create(parent) as PokemonPropertyViewHolder<PokemonProperty>
    }

    override fun onBindViewHolder(holder: PokemonPropertyViewHolder<PokemonProperty>, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onViewAttachedToWindow(holder: PokemonPropertyViewHolder<PokemonProperty>) {
        super.onViewAttachedToWindow(holder)
        holder.setupSubscriptions()
    }

    override fun onViewDetachedFromWindow(holder: PokemonPropertyViewHolder<PokemonProperty>) {
        holder.clearSubscriptions()
        super.onViewDetachedFromWindow(holder)
    }

    fun setItems(items: List<PokemonProperty>?) {
        this.items.clear()
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }
}