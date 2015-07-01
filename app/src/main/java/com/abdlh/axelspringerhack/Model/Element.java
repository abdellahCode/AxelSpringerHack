package com.abdlh.axelspringerhack.Model;

import android.os.Parcelable;

import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory;

/**
 * Created by Abdellah on 6/30/15.
 */
public abstract class Element<T>  {

    protected ViewHolderFactory.ViewHolderType viewHolderType;
    protected String name;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected enum type{
        SPORT(0),
        CULTURE(1),
        SHOPPING(2),
        FOOD(3),
        ACTIVITIES(4),
        RELIGION(5),
        HOTEL(6);

        type(int i) {
        }
    }

    public ViewHolderFactory.ViewHolderType getViewHolderType() {
        return viewHolderType;
    }

    public void setViewHolderType(ViewHolderFactory.ViewHolderType viewHolderType) {
        this.viewHolderType = viewHolderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
