package com.mdgd.pokemon

import androidx.multidex.MultiDexApplication
import com.mdgd.pokemon.models.AppModule
import com.mdgd.pokemon.models_impl.DefaultAppModule

class PokemonsApp : MultiDexApplication() {

    var appComponent: AppModule? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DefaultAppModule(this)
    }

    companion object {
        var instance: PokemonsApp? = null
            private set
    }
}
