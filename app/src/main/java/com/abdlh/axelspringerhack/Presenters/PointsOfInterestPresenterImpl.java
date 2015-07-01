package com.abdlh.axelspringerhack.Presenters;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.Model.PointsOfInterestInteractor;
import com.abdlh.axelspringerhack.UI.Fragments.PointsOfInterestView;
import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public class PointsOfInterestPresenterImpl implements PointsOfInterestPresenter, onLoadingListner{

    PointsOfInterestView pointsOfInterestView;
    PointsOfInterestInteractor pointsOfInterestInteractor;


    public PointsOfInterestPresenterImpl(PointsOfInterestView pointsOfInterestView, PointsOfInterestInteractor pointsOfInterestInteractor){
        this.pointsOfInterestView = pointsOfInterestView;
        this.pointsOfInterestInteractor = pointsOfInterestInteractor;
    }
    @Override
    public void getElements(GoogleApiClient googleApiClient) {
        pointsOfInterestInteractor.fetchPointsOfInterests(googleApiClient, this);
    }

    @Override
    public void onStart(Context context)
    {

    }

    public void onItemClick(Element<?> element, Context context)
    {

    }

    @Override
    public void onElementsLoaded(List<Element<?>> elementList) {
        pointsOfInterestView.setPointsOfInterest(elementList);
    }

    @Override
    public void onError() {

    }
}
