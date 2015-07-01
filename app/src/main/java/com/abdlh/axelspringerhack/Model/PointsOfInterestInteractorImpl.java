package com.abdlh.axelspringerhack.Model;

import android.location.Location;
import android.util.Log;

import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public class PointsOfInterestInteractorImpl implements PointsOfInterestInteractor {
    String TAG = "POI Interactor";
    @Override
    public void fetchPointsOfInterests(GoogleApiClient googleApiClient, onLoadingListner onLoadingListner) {
        final StringBuilder stringBuilder = new StringBuilder();
        PendingResult<PlaceLikelihoodBuffer> mPlaces;
        PlaceFilter placeFilter = new PlaceFilter();
        final List<Element> elementList = new ArrayList<>();
        final PointOfInterest pointOfInterest;
        mPlaces = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        if (mPlaces != null)
        {
            mPlaces.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_ART_GALLERY)) {
                            pointOfInterest = new PointOfInterest();
                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            //pointOfInterest.set
                        }
                        elementList
                        //| Place.TYPE_AMUSEMENT_PARK | Place.TYPE_AQUARIUM))
                        Log.d(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        stringBuilder.append(String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()) + "\n");
                    }
                    placeLikelihoods.release();
                }
            });
        }
        else
            onLoadingListner.onError();
    }
}
