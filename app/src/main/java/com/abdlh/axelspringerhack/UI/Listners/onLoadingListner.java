package com.abdlh.axelspringerhack.UI.Listners;

import com.abdlh.axelspringerhack.Model.Element;

import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public interface onLoadingListner {
    public void onElementsLoaded(List<Element<?>> medicationItems);
    public void onError();
}
