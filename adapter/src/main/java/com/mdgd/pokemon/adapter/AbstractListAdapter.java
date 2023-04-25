package com.mdgd.pokemon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public abstract class AbstractListAdapter<ITEM extends ViewHolderDataItem, VH_PARAMS, EVENT> extends ListAdapter<ITEM, AbstractVH<ITEM>> {

    private final Map<Integer, ViewHolderFactory<VH_PARAMS>> factories = createViewHolderFactories();
    protected final PublishSubject<EVENT> evensSubject;


    public AbstractListAdapter(
            @NonNull DiffUtil.ItemCallback<ITEM> diffCallback
    ) {
        this(diffCallback, null);
    }

    public AbstractListAdapter(
            @NonNull DiffUtil.ItemCallback<ITEM> diffCallback, PublishSubject<EVENT> evensSubject
    ) {
        super(diffCallback);
        this.evensSubject = evensSubject == null ? PublishSubject.create() : evensSubject;
    }

    public AbstractListAdapter(@NonNull AsyncDifferConfig<ITEM> config) {
        this(config, null);
    }

    public AbstractListAdapter(@NonNull AsyncDifferConfig<ITEM> config, PublishSubject<EVENT> evensSubject) {
        super(config);
        this.evensSubject = evensSubject == null ? PublishSubject.create() : evensSubject;
    }

    public Observable<EVENT> getEventsObservable() {
        return evensSubject;
    }

    public boolean isEmpty() {
        return getCurrentList().isEmpty();
    }


    protected abstract Map<Integer, ViewHolderFactory<VH_PARAMS>> createViewHolderFactories();

    protected abstract VH_PARAMS getViewHolderParams(ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewHolderType(position);
    }

    protected View inflate(int layoutResId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }


    @NonNull
    @Override
    public AbstractVH<ITEM> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return factories.get(viewType).createViewHolder(getViewHolderParams(parent, viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractVH<ITEM> holder, int position) {
        holder.onBindViewHolder(getItem(position));
    }

    @Override
    public void onViewAttachedToWindow(@NonNull AbstractVH<ITEM> holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull AbstractVH<ITEM> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }
}
