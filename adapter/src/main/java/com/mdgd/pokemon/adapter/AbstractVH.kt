package com.mdgd.pokemon.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractVH<ITEM>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var model: ITEM? = null

    open fun onViewAttachedToWindow() {}

    open fun onViewDetachedFromWindow() {}

    protected fun hasModel(): Boolean = model != null

    protected open fun bindSame(): Boolean = false

    fun onBindViewHolder(item: ITEM) {
        if (!bindSame() && model === item) {
            return
        }
        model = item
        bind(item)
    }

    open fun bind(item: ITEM) {}
}
