package com.mdgd.pokemon.adapter;

public interface ViewHolderFactory<PARAM> {
    AbstractVH createViewHolder(PARAM params);
}
