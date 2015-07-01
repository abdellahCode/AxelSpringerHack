package com.abdlh.axelspringerhack.Model;

import android.content.Context;
import android.location.Location;

import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created by Abdellah on 6/30/15.
 */
public interface PointsOfInterestInteractor {

    void fetchPointsOfInterests(GoogleApiClient googleApiClient, onLoadingListner onLoadingListner, Context contexts);
}
