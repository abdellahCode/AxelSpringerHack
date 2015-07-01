package com.abdlh.axelspringerhack.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.RawRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.abdlh.axelspringerhack.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.util.regex.Pattern;


/**
 * <p>
 * This class provides view related convenience methods.
 * </p>
 *
 * @author bhinuebe, tgieseck, ckohlwage, dzorn
 * @version 1
 */
public abstract class ViewUtils
{
    public static final String TAG = ViewUtils.class.getSimpleName();


    /**
     * Intentionally <code>private</code>.
     */
    private ViewUtils()
    {
    }


    /**
     * Konvertiert die angegebene Breite in eine Höhe im Verhältnis 16:9
     */
    public static int ratio16to9fromWidth(final int width)
    {
        return (int) (width * 0.5625f);
    }

    /**
     * Konvertiert die angegebene Höhe in eine Breite im Verhältnis 16:9
     */
    public static int ratio16to9fromHeight(final int height)
    {
        return (int) (height * 1.7778f);
    }


    /**
     * Erzeugt ein Drawable aus einem SVG-File innerhalb der Assets.
     * Es werden die Groessenangaben im SVG-Dokument verwendet.
     */
    public static Drawable getDrawableFromSVG(final Context context, final String filename)
    {
        Drawable drawable = null;
        try
        {
            SVG svg = SVG.getFromAsset(context.getAssets(), filename);
            drawable = new PictureDrawable(svg.renderToPicture());
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error in getDrawableFromSVG", e);
        }
        return drawable;
    }

    /**
     * Erzeugt ein Drawable aus einem SVG-File innerhalb der Resources.
     * Es werden die Groessenangaben im SVG-Dokument verwendet.
     */
    public static Drawable getDrawableFromSVG(final Context context, final @RawRes int resId)
    {
        Drawable drawable = null;
        try
        {
            SVG svg = SVG.getFromResource(context, resId);
            drawable = new PictureDrawable(svg.renderToPicture());
        }
        catch (SVGParseException e)
        {
            Log.e(TAG, "Error in getDrawableFromSVG", e);
        }
        return drawable;
    }

    /**
     * Erzeugt ein Drawable aus einem SVG-File innerhalb der Resources.
     * Der Pixelwert definiert dabei die Breite und Hoehe des PictureDrawable,
     * in die das SVG-Dokument gerendert wird.
     * <p/>
     * <b>Achtung: Der Layertype der View-Komponente muss auf Software-Rendering umgestellt werden.</b>
     */
    public static Drawable getDrawableFromSvgPx(final Context context, final @RawRes int resId, float px)
    {
        Drawable drawable = null;
        try
        {
            SVG svg = SVG.getFromResource(context, resId);
            svg.setDocumentHeight(px);
            svg.setDocumentWidth(px);

            drawable = new PictureDrawable(svg.renderToPicture());
        }
        catch (SVGParseException e)
        {
            Log.e(TAG, "Error in getDrawableFromSVG", e);
        }
        return drawable;
    }

    /**
     * Set left compound TextView drawable. Load the drawable with the given res id from the resource directory
     * and sets the layer type of the given TextView to software rendering.
     *
     * @param view TextView object
     * @param res  resource id
     */
    public static void setTextViewSVGCompoundDrawableLeft(final Context context, final TextView view, final @RawRes int res, final int dimRes)
    {
        final Drawable icon = ViewUtils.getDrawableFromSvg(context, res, dimRes);
        if (icon != null && view != null)
        {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            view.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        }
    }


    /**
     * Sets a SVG as icon for an menu item.
     */
    public static void setMenuIcon(Context context, Menu menu, @IdRes int menuItemId, @RawRes int svgResourceId)
    {
        setTintedMenuIcon(context, menu, menuItemId, svgResourceId, 0);
    }

    /**
     * Sets a SVG as icon for an menu item and tints it.
     */
    public static void setTintedMenuIcon(Context context, Menu menu, @IdRes int menuItemId, @RawRes int svgResourceId, @ColorRes final int colorId)
    {
        final MenuItem menuItem = menu.findItem(menuItemId);
        if (context != null && menuItem != null)
        {
           Drawable drawable = getDrawableFromSvg(context, svgResourceId, R.dimen.material_icon_height);
           if (colorId != 0)
           {
               DrawableCompat.wrap(drawable);
               DrawableCompat.setTintMode(drawable, Mode.SRC_OUT);
               DrawableCompat.setTint(drawable, colorId);
           }
           menuItem.setIcon(drawable);
        }
        else
        {
            Log.w(TAG, "setMenuIcon failed for menuItemId " + menuItemId + " svgResourceId" + svgResourceId);
        }
    }

    /**
     * Returns a PictureDrawable from a SVG raw resource with a given height (dp), <code>null</code> if the context is invalid.
     *
     * @param rawResId svg file id in the raw directory
     * @param dimResId dimension value id
     * @return new Drawable or null, if svg not exist or given context is null
     */
    public static Drawable getDrawableFromSvg(final Context context, final @RawRes int rawResId, final int dimResId)
    {
        Drawable drawable = null;
        try
        {
            if (context != null)
            {
                final Resources r = context.getResources();
                final float px = (float) r.getDimensionPixelSize(dimResId);
                drawable = ViewUtils.getDrawableFromSvgPx(context, rawResId, px);
            }
        }
        catch (NotFoundException e)
        {
            Log.e(TAG, e.getMessage());
        }

        return drawable;
    }

    /**
     * Returns a PictureDrawable from a SVG raw resource with a given height (dp) and tints it, <code>null</code> if the context is invalid.
     *
     * @param rawResId svg file id in the raw directory
     * @param dimResId dimension value id
     * @param colorId res Id of color to tint
     * @return new Drawable or null, if svg not exist or given context is null
     */
    public static Drawable getTintedDrawableFromSvg(final Context context, final @RawRes int rawResId, final int dimResId, @ColorRes final int colorId)
    {
        Drawable drawable = null;
        try
        {
            if (context != null)
            {
                final Resources r = context.getResources();
                final float px = (float) r.getDimensionPixelSize(dimResId);
                drawable = ViewUtils.getDrawableFromSvgPx(context, rawResId, px);
                DrawableCompat.wrap(drawable);
                DrawableCompat.setTintMode(drawable, Mode.SRC_IN);
                DrawableCompat.setTint(drawable, colorId);
            }
        }
        catch (NotFoundException e)
        {
            Log.e(TAG, e.getMessage());
        }

        return drawable;
    }

    /**
     * Sets the given {@link CharSequence} if not empty or <code>null</code>,
     * otherwise the visibility of the {@link TextView} will be changed to
     * {@link View#GONE}.
     *
     * @param charSequence the {@link CharSequence} to set
     * @param textView     the associated {@link TextView}
     * @return <code>true<code>, if the {@link CharSequence} could be set, otherwise <code>false</code>
     * .
     */
    public static boolean setTextOrHide(final CharSequence charSequence,
                                        final TextView textView)
    {
        boolean result = false;
        if (textView != null)
        {
            if (TextUtils.isEmpty(charSequence))
            {
                textView.setVisibility(View.GONE);
            }
            else
            {
                textView.setText(charSequence);
                textView.setVisibility(View.VISIBLE);
                result = true;
            }
        }
        return result;
    }

    /*public static int getStatusBarHeight(final Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }*/
}