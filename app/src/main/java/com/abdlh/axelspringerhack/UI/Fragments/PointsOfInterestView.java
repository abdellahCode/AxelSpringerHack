package com.abdlh.axelspringerhack.UI.Fragments;

import com.abdlh.axelspringerhack.Model.Element;

import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public interface PointsOfInterestView {
    void setPointsOfInterest(List<Element<?>> medicationsList);
    void showLoading();
    void hideLoading();
    void onErrorLoading();}
