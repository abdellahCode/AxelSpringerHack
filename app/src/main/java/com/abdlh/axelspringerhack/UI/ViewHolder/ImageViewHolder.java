package com.abdlh.axelspringerhack.UI.ViewHolder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdlh.axelspringerhack.Model.Image;
import com.abdlh.axelspringerhack.Model.PointOfInterest;
import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.Utils.ViewUtils;
import com.squareup.picasso.Picasso;


/**
 * Created by ckohlwag on 01.07.15.
 */
public class ImageViewHolder extends ViewHolderExt<Image>
{
    final ImageView imageView;
    final TextView title;
    final LinearLayout titlelayout;

    public ImageViewHolder(View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        titlelayout = (LinearLayout) itemView.findViewById(R.id.titlelayout);

    }

    @Override
    public void onBind(Image mPoi, int position)
    {
        titlelayout.setVisibility(View.VISIBLE);
        if (mPoi != null)
        {
            ViewUtils.setTextOrHide(mPoi.getText(), title);
            if (TextUtils.isEmpty(mPoi.getText()))
            {
                titlelayout.setVisibility(View.GONE);
            }
            if (mPoi.getSvgRes() != 0)
            {

                imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                imageView.setImageDrawable(ViewUtils.getDrawableFromSVG(itemView.getContext(), mPoi.getSvgRes()));
            }
            else if(mPoi.getImageRes() != 0)
            {
                imageView.setImageDrawable(itemView.getContext().getResources().getDrawable(mPoi.getImageRes(), null));
            }
            else
            {
                Picasso.with(itemView.getContext()).load(mPoi.getImageUrl()).into(imageView);
            }
        }
    }

}