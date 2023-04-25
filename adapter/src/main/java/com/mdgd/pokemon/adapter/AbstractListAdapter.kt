package com.mdgd.pokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class AbstractListAdapter<
        ITEM : ViewHolderDataItem, VH_PARAMS, EVENT
        > : ListAdapter<ITEM, AbstractVH<ITEM>> {

    private val factories = createViewHolderFactories()
    protected val evensSubject: MutableSharedFlow<EVENT>

    @JvmOverloads
    constructor(
        diffCallback: DiffUtil.ItemCallback<ITEM>, evensSubject: MutableSharedFlow<EVENT>? = null
    ) : super(diffCallback) {
        this.evensSubject = evensSubject ?: MutableSharedFlow(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    @JvmOverloads
    constructor(
        config: AsyncDifferConfig<ITEM>,
        evensSubject: MutableSharedFlow<EVENT>? = null
    ) : super(config) {
        this.evensSubject = evensSubject ?: MutableSharedFlow(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    fun getEventsObservable(): Flow<EVENT> = evensSubject

    fun isEmpty(): Boolean = currentList.isEmpty()

    protected abstract fun createViewHolderFactories(): Map<Int, ViewHolderFactory<VH_PARAMS>>

    protected abstract fun getViewHolderParams(parent: ViewGroup, viewType: Int): VH_PARAMS

    override fun getItemViewType(position: Int): Int =
        getItem(position)!!.getViewHolderType(position)

    protected fun inflate(layoutResId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractVH<ITEM> {
        return factories[viewType]!!.createViewHolder(
            getViewHolderParams(
                parent,
                viewType
            )
        ) as AbstractVH<ITEM>
    }

    override fun onBindViewHolder(holder: AbstractVH<ITEM>, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: AbstractVH<ITEM>) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: AbstractVH<ITEM>) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }
}