package com.abdlh.axelspringerhack.UI.Adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;

import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.Model.Image;
import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderExt;
import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory;
import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory.ViewHolderType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckohlwag on 01.07.15.
 */
public class LociAdapter extends RecyclerView.Adapter<ViewHolder>
{
    private List<Element<?>> mPoiList;
    private final FragmentActivity mActivity;

    public LociAdapter(List<Element<?>> mPoiList, FragmentActivity activity)
    {
        this.mPoiList = mPoiList;
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final ViewHolderExt<?> viewHolder;
        Log.d("LociAdapter", "onCreateViewHolder");
            final ViewHolderFactory.ViewHolderType viewHolderType = ViewHolderType.values()[viewType];
            viewHolder = ViewHolderFactory.getViewHolder(parent, viewHolderType);
//            viewHolder.setOnSelectedChangedListener(this);
//            viewHolder.setOnRefreshRequestedListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
//        ((ViewHolderExt) holder).setSelectionState(selectionStates.get(position));


        final Element<?> element = mPoiList.get(position);
        ((ViewHolderExt) holder).bind(element, position);
        Log.d("LociAdapter", "binding "+element.getViewHolderType().value +" on position " + position);
    }

    @Override
    public int getItemCount()
    {
        return mPoiList == null ? 0 : mPoiList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        final ViewHolderType viewType = mPoiList.get(position).getViewHolderType();
        final int result;

            // No ad - just map nodetype to viewtype:
            result = ViewHolderType.indexOf(viewType);

        return result;
    }
}
