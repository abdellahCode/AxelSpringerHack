package com.abdlh.axelspringerhack.Model;

import android.location.Location;

/**
 * Created by Abdellah on 6/30/15.
 */
public class PointOfInterest extends Element {
    Location location;
    String Text;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
