package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.abdlh.axelspringerhack.Model.PointOfInterest;
import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.UI.Views.BildImageView;
import com.squareup.picasso.Picasso;


/**
 * Created by ckohlwag on 01.07.15.
 */
public class PointOfInterestViewHolder extends ViewHolderExt<PointOfInterest>
{
    final BildImageView imageView;
    final TextView title;
    final TextView distance;

    public PointOfInterestViewHolder(View itemView)
    {
        super(itemView);

        imageView = (BildImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        distance = (TextView) itemView.findViewById(R.id.distance);

    }

    @Override
    public void onBind(PointOfInterest mPoi, int position)
    {
        title.setText(mPoi.getText());
        distance.setText(mPoi.getDistance()+ " km");
        Picasso.with(itemView.getContext()).load("http://www.axelspringerhackday.de/wp-content/uploads/2015/05/head-ashd.png").into(imageView);
    }

}