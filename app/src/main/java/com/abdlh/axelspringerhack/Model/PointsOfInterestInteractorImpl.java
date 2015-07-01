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
    public void fetchPointsOfInterests(GoogleApiClient googleApiClient, final onLoadingListner onLoadingListner) {
        final StringBuilder stringBuilder = new StringBuilder();
        PendingResult<PlaceLikelihoodBuffer> mPlaces;
        PlaceFilter placeFilter = new PlaceFilter();
        final List<Element<?>> elementList = new ArrayList<>();
        mPlaces = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        if (mPlaces != null)
        {
            mPlaces.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    PointOfInterest pointOfInterest = null;
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        pointOfInterest = new PointOfInterest(null, null, 0);
                        if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_ART_GALLERY | Place.TYPE_LIBRARY
                                | Place.TYPE_MUSEUM | Place.TYPE_PAINTER | Place.TYPE_UNIVERSITY)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.CULTURE);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_AQUARIUM | Place.TYPE_AMUSEMENT_PARK
                                | Place.TYPE_BOWLING_ALLEY | Place.TYPE_BAR | Place.TYPE_CAFE | Place.TYPE_CASINO | Place.TYPE_SPA)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.ACTIVITIES);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CHURCH | Place.TYPE_MOSQUE
                                | Place.TYPE_SYNAGOGUE | Place.TYPE_HINDU_TEMPLE | Place.TYPE_PLACE_OF_WORSHIP)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.RELIGION);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_SHOPPING_MALL | Place.TYPE_SHOE_STORE
                                | Place.TYPE_CONVENIENCE_STORE | Place.TYPE_JEWELRY_STORE)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.SHOPPING);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CAFE | Place.TYPE_FOOD
                                | Place.TYPE_RESTAURANT)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.FOOD);
                        }
                        else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_STADIUM | Place.TYPE_GYM)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.SPORT);
                        }
                        else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CAMPGROUND)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.HOTEL);
                        }

                        elementList.add(pointOfInterest);
                        //| Place.TYPE_AMUSEMENT_PARK | Place.TYPE_AQUARIUM))
                        Log.d(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        stringBuilder.append(String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()) + "\n");
                    }
                    onLoadingListner.onElementsLoaded(elementList);
                    placeLikelihoods.release();
                }
            });
        }
        else
            onLoadingListner.onError();
    }
}
