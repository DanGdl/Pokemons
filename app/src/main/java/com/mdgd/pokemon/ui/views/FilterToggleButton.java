package com.mdgd.pokemon.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatToggleButton;

import com.mdgd.pokemon.R;

public class FilterToggleButton extends AppCompatToggleButton {

    private final int icon;

    public FilterToggleButton(@NonNull Context context) {
        this(context, null);
    }

    public FilterToggleButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyleToggle);
    }

    public FilterToggleButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FilterButtonIcon);
        icon = a.getResourceId(R.styleable.FilterButtonIcon_icons, -1);
        a.recycle();
    }


    // todo draw background properly

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
