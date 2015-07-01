package com.abdlh.axelspringerhack.Model;

import android.location.Location;

import java.util.List;

/**
 * Created by Abdellah on 6/30/15.
 */
public interface PointsOfInterestInteractor {

    List<Element<?>> fetchPointsOfInterests(Location location);
}
