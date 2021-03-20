package com.mdgd.pokemon.ui.adapter

sealed class ClickEvent<T> {

    class EmptyData<T>() : ClickEvent<T>()

    class ClickData<T>(val data: T) : ClickEvent<T>()
}
