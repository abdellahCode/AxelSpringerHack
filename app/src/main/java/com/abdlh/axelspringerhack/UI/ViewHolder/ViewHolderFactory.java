package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdlh.axelspringerhack.R;

/**
 * Created by Abdellah on 6/30/15.
 */
public class ViewHolderFactory {
    public static ViewHolderExt getViewHolder(ViewGroup viewGroup, ViewHolderType viewHolderType){
        ViewHolderExt viewHolder = null;

        switch (viewHolderType){

            case POI:
                viewHolder = new PointOfInterestViewHolder(inflate(R.layout.viewholder_poi, viewGroup));
                break;
        }



        return viewHolder;
    }

    /**
     * Inflates the given layout resource and fixes the ripple hotspot.
     * Requires a android:background="?android:attr/selectableItemBackground" at the first child view (container) of the CardView.
     * http://commonsware.com/blog/2015/02/06/ripples-touch-point.html
     */
    static View inflate(final @LayoutRes int id, final @NonNull ViewGroup parent)
    {
        final View view = LayoutInflater.from(parent.getContext()).inflate(id, parent, false);

        return view;
    }

    public enum ViewHolderType{

        POI("pointOfInterest");

        public final String value;

        ViewHolderType(String value)
        {
            this.value = value;
        }
    }
}
