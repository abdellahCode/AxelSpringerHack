package com.abdlh.axelspringerhack.Presenters;

import android.content.Context;
import android.location.Location;

import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public interface PointsOfInterestPresenter {
    void  getElements(GoogleApiClient googleApiClient);
    void onStart(Context context);
    void onItemClick(Element<?> element, Context context);
}
