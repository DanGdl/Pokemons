package com.mdgd.pokemon.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractVH<ITEM> extends RecyclerView.ViewHolder {
    protected ITEM model = null;

    public AbstractVH(@NonNull View itemView) {
        super(itemView);
    }

    public void onViewAttachedToWindow() {

    }

    public void onViewDetachedFromWindow() {

    }

    protected boolean hasModel() {
        return model != null;
    }

    protected boolean bindSame() {
        return false;
    }

    public void onBindViewHolder(ITEM item) {
        if (!bindSame() && model == item) {
            return;
        }
        model = item;
        bind(item);
    }

    public void bind(ITEM item) {

    }
}
