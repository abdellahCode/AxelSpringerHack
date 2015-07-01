package com.abdlh.axelspringerhack.Model;

import android.location.Location;

import com.abdlh.axelspringerhack.UI.ViewHolder.ViewHolderFactory.ViewHolderType;

/**
 * Created by ckohlwag on 6/30/15.
 */
public class Image extends Element {
    private String imageUrl;
    private int imageRes;
    private int svgRes;
    private String text;
    private type type;


    public Element.type getType() {
        return type;
    }

    public void setType(final Element.type type) {
        this.type = type;
    }

    public Image(final String imageUrl, final String text){
        this.imageUrl = imageUrl;
        this.text = text;
        viewHolderType = ViewHolderType.IMAGE;
    }


    public Image(final int svgRes, final String text){
        this.svgRes = svgRes;
        this.text = text;
        viewHolderType = ViewHolderType.IMAGE;
    }

    public Image(final String text, final int imageRes){
        this.imageRes = imageRes;
        this.text = text;
        viewHolderType = ViewHolderType.IMAGE;
    }

    @Override
    public String getImageUrl()
    {
        return imageUrl;
    }

    @Override
    public void setImageUrl(final String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }

    public int getSvgRes()
    {
        return svgRes;
    }

    public void setSvgRes(int svgRes)
    {
        this.svgRes = svgRes;
    }

    public int getImageRes()
    {
        return imageRes;
    }

    public void setImageRes(int imageRes)
    {
        this.imageRes = imageRes;
    }
}
