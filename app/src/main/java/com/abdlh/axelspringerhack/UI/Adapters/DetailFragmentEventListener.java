package com.abdlh.axelspringerhack.UI.Adapters;

/**
 * Created by ckohlwag on 30.07.15.
 */
interface DetailFragmentEventListener
{
    void toggleProgress(boolean inProgress);

    void handleDetails();

    void popCurrentFragmentFromBackStack();

    void popBackStackToMeineBildFragment();
}
