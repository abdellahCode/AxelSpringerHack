package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdlh.axelspringerhack.MainActivity;
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
    public void onBind(final PointOfInterest mPoi, int position)
    {

        if (mPoi != null)
        {
            ViewUtils.setTextOrHide(mPoi.getName(), title);
            ViewUtils.setTextOrHide(mPoi.getDistance() == null ? "145 m" :  mPoi.getDistance() + " m", distance);

            Picasso.with(itemView.getContext()).load(mPoi.getImageUrl()).into(imageView);
        }

        imageView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(itemView.getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", mPoi.getName());
                bundle.putBoolean("detail", true);
                intent.putExtra("extras", bundle);
                itemView.getContext().startActivity(intent);

            }
        });
    }
}