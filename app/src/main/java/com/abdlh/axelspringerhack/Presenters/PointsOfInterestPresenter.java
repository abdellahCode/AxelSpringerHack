package com.abdlh.axelspringerhack.Presenters;

import android.content.Context;
import android.location.Location;

import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;

import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public interface PointsOfInterestPresenter {
    List<Element<?>> getElements(onLoadingListner onLoadingListner);
    void onStart(Context context);
    void onItemClick(Element<?> element, Context context);
}
