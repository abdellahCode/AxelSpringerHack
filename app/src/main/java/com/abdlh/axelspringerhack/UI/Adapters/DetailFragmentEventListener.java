package com.abdlh.axelspringerhack.UI.Adapters;

import com.abdlh.axelspringerhack.Model.PointOfInterest;

/**
 * Created by ckohlwag on 30.07.15.
 */
public interface DetailFragmentEventListener
{
    void toggleProgress(boolean inProgress);

    void handleDetails(PointOfInterest mPoi);

    void popCurrentFragmentFromBackStack();

    void popBackStackToMeineBildFragment();
}
