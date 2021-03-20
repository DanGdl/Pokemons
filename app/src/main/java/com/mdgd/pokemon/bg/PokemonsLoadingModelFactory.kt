package com.mdgd.pokemon.bg

import com.mdgd.pokemon.models.AppModule

class PokemonsLoadingModelFactory(var appComponent: AppModule) {

    fun create(): ServiceModel {
        return LoadPokemonsModel(appComponent.getPokemonsRepo(), appComponent.getCache())
    }
}
