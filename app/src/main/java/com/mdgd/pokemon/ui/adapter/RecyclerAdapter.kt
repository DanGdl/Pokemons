package com.mdgd.pokemon.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class RecyclerAdapter<T> : RecyclerView.Adapter<RecyclerVH<T>>() {
    protected val items: MutableList<T> = ArrayList()
    protected val clicksSubject = MutableStateFlow<ClickEvent<T>>(ClickEvent.EmptyData())

    fun getItemClickFlow(): Flow<ClickEvent<T>> {
        return clicksSubject
    }

    override fun getItemViewType(position: Int): Int {
        if (items.isEmpty()) {
            return EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 1 else items.size
    }

    override fun onBindViewHolder(holder: RecyclerVH<T>, position: Int) {
        if (items.isNotEmpty()) {
            holder.bindItem(items[position], position)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerVH<T>) {
        super.onViewAttachedToWindow(holder)
        holder.setupSubscriptions()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerVH<T>) {
        holder.clearSubscriptions()
        super.onViewDetachedFromWindow(holder)
    }

    fun setItems(items: List<T>) {
        if (this.items.isEmpty()) {
            this.items.addAll(items)
            notifyDataSetChanged()
        } else {
            val oldList: List<T> = ArrayList(this.items)
            this.items.clear()
            this.items.addAll(items)
            dispatchChanges(oldList, items)
        }
    }

    protected fun dispatchChanges(oldList: List<T>, items: List<T>) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldList.size
            }

            override fun getNewListSize(): Int {
                return items.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == items[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == items[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }


    companion object {
        const val EMPTY_VIEW = 1
    }
}
