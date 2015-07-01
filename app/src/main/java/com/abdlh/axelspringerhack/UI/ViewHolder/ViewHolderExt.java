package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import java.util.List;


/**
 * Base class that enriches RecyclerView.ViewHolders with event handling and generic bindings.
 */
public abstract class ViewHolderExt<T> extends ViewHolder implements OnClickListener, Bindable<T>
{
    /*public interface OnSelectedChangedListener
    {
        void onSelectedChanged(int position, boolean isSelected, boolean clearOthers);
    }

    public interface OnInsertionListener
    {
        void onInsertItems(int position, List<? extends Element> items);
    }

    public interface OnRefreshRequestedListener
    {
        void onRefreshRequested();
    }

    private OnClickListener listener;
    private String modifyingSharedPreference;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    protected final boolean isLandscape;
    protected final boolean useBlockLayout;
    protected boolean isSelected;
    protected OnSelectedChangedListener onSelectedChangedListener;
    protected OnInsertionListener onInsertionListener;
    protected OnRefreshRequestedListener onRefreshRequestedListener;

    public void setOnClickListener(final OnClickListener listener)
    {
        this.listener = listener;
    } */

    protected int position;
    /**
     * Creates a new instance of the specific view holder.
     *
     * @param itemView the parent view of the inflated layout.
     */
    public ViewHolderExt(@NonNull View itemView)
    {
        super(itemView);
        itemView.setClickable(true);
        itemView.setFocusable(true);
        itemView.setHapticFeedbackEnabled(true);
        itemView.setSoundEffectsEnabled(true);
        // intercepts the first touch event to the viewholder
        itemView.setFocusableInTouchMode(false);
//        itemView.setOnClickListener(this);
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
        {

            @Override
            public void onViewAttachedToWindow(View v)
            {

                /*if (prefListener != null)
                {
                    MainApp.prefs().registerOnSharedPreferenceChangeListener(prefListener);
                }*/
                ViewHolderExt.this.onViewAttachedToWindow();
            }

            @Override
            public void onViewDetachedFromWindow(View v)
            {
                /*if (prefListener != null)
                {
                    MainApp.prefs().unregisterOnSharedPreferenceChangeListener(prefListener);
                }
                onSelectedChangedListener = null;*/
                ViewHolderExt.this.onViewDetachedFromWindow();
            }
        });

//        isLandscape = ViewUtils.isLandscape(itemView.getContext());
    }

    @Override
    public void onClick(View v)
    {
        /*if (listener != null)
        {
            listener.onClick(v);
        }*/
    }

    @Override
    public void bind(final T data, final int position)
    {
        this.position = position;
        onBind(data, position);
    }

    protected abstract void onBind(final T data, final int position);

    protected void invalidate()
    {
        if (itemView != null)
        {
            itemView.invalidate();
        }
    }

    /**
     * Stub. Optionale Implementierung falls benötigt.
     */
    protected void reload()
    {
    }

    /**
     * Registriert einen preference change listener, der einen automatischen Reload auslöst,
     * sobald key geändert wurde.
     */
   /* protected void setReloadUponPreferenceChanged(final String key)
    {
        if (prefListener != null)
        {
            return;
        }
        modifyingSharedPreference = key;
        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener()
        {
            @Override
            public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key)
            {
                if (key.equals(modifyingSharedPreference))
                {
                    reload();
                }
            }
        };

        MainApp.prefs().registerOnSharedPreferenceChangeListener(prefListener);
    }*/

    /**
     * Sets the margins in dp of the given View.
     */
    protected void setMarginsDp(View view, int left, int top, int right, int bottom)
    {
        if (view != null)
        {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (params != null)
            {
                params.setMargins(left, top, right, bottom);
                view.setLayoutParams(params);
            }
        }
    }

    /**
     * Sets the margins in dp of the ViewHolder's itemView (the parent element of a layout).
     */
    protected void setMarginsDp(int left, int top, int right, int bottom)
    {
        setMarginsDp(itemView, left, top, right, bottom);
    }

    protected void onViewAttachedToWindow() {}

    protected void onViewDetachedFromWindow() {}

    /*public void setOnSelectedChangedListener(OnSelectedChangedListener onSelectedChangedListener)
    {
        this.onSelectedChangedListener = onSelectedChangedListener;
    }

    public void setOnInsertionListener(OnInsertionListener listener) {
        this.onInsertionListener = listener;
    }

    public void setOnRefreshRequestedListener(OnRefreshRequestedListener listener) {
        this.onRefreshRequestedListener = listener;
    }

    protected void notifySelectionChanged(boolean isSelected, boolean clearOthers)
    {
        if (onSelectedChangedListener != null)
        {
            onSelectedChangedListener.onSelectedChanged(position, isSelected, clearOthers);
        }
        this.isSelected = isSelected;
    }

    public void setSelectionState(boolean selectionState)
    {
        this.isSelected = selectionState;
    }

    protected void notifyRefreshRequested()
    {
        if (onRefreshRequestedListener != null)
        {
            onRefreshRequestedListener.onRefreshRequested();
        }
    }*/
}