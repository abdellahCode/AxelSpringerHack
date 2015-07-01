package com.abdlh.axelspringerhack.Model;

import android.location.Location;

import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory.ViewHolderType;

import java.util.List;

/**
 * Created by Abdellah on 6/30/15.
 */
public class PoiDetailHorizontalGallery extends Element {
    private List<Element<Image>> imageList;
    private String text;
    private type type;


    public Element.type getType() {
        return type;
    }

    public void setType(final Element.type type) {
        this.type = type;
    }

    public PoiDetailHorizontalGallery(final List<Element<Image>> imageList, final String text){
        viewHolderType = ViewHolderType.HORIZONTAL_GALLERY;
        this.text = text;
        this.imageList = imageList;
    }

    public List<Element<Image>> getImageList()
    {
        return imageList;
    }

    public void setImageList(List<Element<Image>> imageList)
    {
        this.imageList = imageList;
    }

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }
}
