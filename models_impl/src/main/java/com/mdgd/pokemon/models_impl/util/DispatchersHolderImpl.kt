package com.mdgd.pokemon.models_impl.util

import com.mdgd.pokemon.models.util.DispatchersHolder
import kotlinx.coroutines.Dispatchers

class DispatchersHolderImpl : DispatchersHolder {

    override fun getMain() = Dispatchers.Main

    override fun getIO() = Dispatchers.IO
}
