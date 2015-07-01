package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.location.LocationManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdlh.axelspringerhack.Model.PointOfInterest;
import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.Utils.ViewUtils;
import com.squareup.picasso.Picasso;


/**
 * Created by ckohlwag on 01.07.15.
 */
public class PointOfInterestViewHolder extends ViewHolderExt<PointOfInterest>
{
    final ImageView imageView;
    final TextView title;
    final TextView distance;

    public PointOfInterestViewHolder(View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        distance = (TextView) itemView.findViewById(R.id.distance);

    }

    @Override
    public void onBind(PointOfInterest mPoi, int position)
    {

        ViewUtils.setTextOrHide(mPoi.getName(), title);
        ViewUtils.setTextOrHide(mPoi.getDistance()+ " km", distance);

        Picasso.with(itemView.getContext()).load(mPoi.getImageUrl()).into(imageView);
    }

}