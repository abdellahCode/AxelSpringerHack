package com.abdlh.axelspringerhack.Model;

import android.location.Location;

import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory.ViewHolderType;

/**
 * Created by Abdellah on 6/30/15.
 */
public class PointOfInterest extends Element {
    private Location location;
    private String imageUrl;
    private Float distance;
    private type type;

    public Element.type getType() {
        return type;
    }

    public void setType(Element.type type) {
        this.type = type;
    }

    public PointOfInterest(String s, String s1, int i){
        viewHolderType = ViewHolderType.POI;
    }

    public PointOfInterest(String name, String imageUrl, Float distance)
    {
        this.name = name;
        this.imageUrl = imageUrl;
        this.distance = distance;
        viewHolderType = ViewHolderType.POI;
        this.type = type;
    }

    public PointOfInterest(Location location, String name, String imageUrl, Float distance)
    {
        this.location = location;
        this.name = name;
        this.imageUrl = imageUrl;
        this.distance = distance;
        viewHolderType = ViewHolderType.POI;
        this.type = type;
    }



    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}
