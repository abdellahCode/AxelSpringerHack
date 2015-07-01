package com.abdlh.axelspringerhack.Model;

import android.location.Location;

/**
 * Created by Abdellah on 6/30/15.
 */
public class PointOfInterest extends Element {
    private Location location;
    private String title;
    private String imageUrl;
    private Float distance;

    public PointOfInterest(Location location, String title, String imageUrl, Float distance)
    {
        this.location = location;
        this.title = title;
        this.imageUrl = imageUrl;
        this.distance = distance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getText() {
        return title;
    }

    public void setText(String text) {
        title = text;
    }
}
