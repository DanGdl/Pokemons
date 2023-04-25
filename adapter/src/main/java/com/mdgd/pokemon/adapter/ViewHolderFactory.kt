package com.mdgd.pokemon.adapter

interface ViewHolderFactory<PARAM> {
    fun createViewHolder(params: PARAM): AbstractVH<*>
}
