package com.mdgd.pokemon.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class RecyclerVH<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun setupSubscriptions() {
    }

    open fun clearSubscriptions() {
    }

    open fun bindItem(item: T, position: Int) {
    }
}
