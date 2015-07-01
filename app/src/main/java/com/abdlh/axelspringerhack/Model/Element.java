package com.abdlh.axelspringerhack.Model;

import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory;

/**
 * Created by Abdellah on 6/30/15.
 */
public abstract class Element<T> {

    protected ViewHolderFactory.ViewHolderType viewHolderType;
    protected String name;

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
