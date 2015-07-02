package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.fragment_click;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdellah on 6/30/15.
 */
public class ViewHolderFactory {
    private static final String TAG = ViewHolderFactory.class.getSimpleName();

    public static ViewHolderExt getViewHolder(ViewGroup viewGroup, ViewHolderType viewHolderType, fragment_click fragment_click){
        ViewHolderExt viewHolder = null;

        switch (viewHolderType){

            case POI:
                viewHolder = new PointOfInterestViewHolder(inflate(R.layout.viewholder_poi, viewGroup), fragment_click);
                break;
            case IMAGE:
                viewHolder = new ImageViewHolder(inflate(R.layout.viewholder_image, viewGroup));
                break;
            case HORIZONTAL_GALLERY:
                break;
            case UNKNOWN:
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

        POI("pointOfInterest"),
        IMAGE("image"),
        HORIZONTAL_GALLERY("horizontal_gallery"),
        UNKNOWN("Unknown");

        public final String value;

        ViewHolderType(String value)
        {
            this.value = value;
        }

        private final static Map<String, ViewHolderType> nodeTypes = new HashMap<>();
        private final static Map<ViewHolderType, Integer> nodeTypeIndex = new HashMap<>();

        private static final String TAG = ViewHolderType.class.getSimpleName();

        static
        {
            int index = 0;
            for (ViewHolderType nodeType : ViewHolderType.values())
            {
                nodeTypes.put(nodeType.value, nodeType);
                nodeTypeIndex.put(nodeType, index++);
            }
        }

        public static ViewHolderType byValue(String value)
        {
            ViewHolderType nodeType = nodeTypes.get(value);
            if (nodeType != null)
            {
                return nodeType;
            }
            Log.w(TAG, "Found unknown node type: " + value);
            return UNKNOWN;
        }

        public static int indexOf(ViewHolderType nodeType)
        {
            return nodeTypeIndex.get(nodeType);
        }

    }
}
