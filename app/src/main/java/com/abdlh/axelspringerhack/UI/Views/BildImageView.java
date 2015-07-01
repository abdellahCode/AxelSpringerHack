package com.abdlh.axelspringerhack.UI.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;


/**
 * Created by ckarth on 12.02.15.
 */
public class BildImageView extends ImageView
{
    protected static final int DEFAULT_IMAGE_WIDTH = 1920;
    protected static final int DEFAULT_IMAGE_HEIGHT = 1080;
protected int imageHeight;
    protected int imageWidth;
    private boolean attachedToWindow;
    private RectF iconBounds;
    private SVG svg;
    private float iconRelativeSize;

    public BildImageView(Context context)
    {
        super(context);
        initialize(null);
    }

    public BildImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize(attrs);
    }

    public BildImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs)
    {
        attachedToWindow = false;
        iconBounds = new RectF();
        imageWidth = DEFAULT_IMAGE_WIDTH;
        imageHeight = DEFAULT_IMAGE_HEIGHT;

    }

    public void setImageDimension(final int width, final int height)
    {
        if (height > 0)
        {
            this.imageHeight = height;
        }

        if (width > 0)
        {
            this.imageWidth = width;
        }
    }

    protected int getDesiredImageViewHeight(final int parentWidth)
    {
        final float aspectRatioFactor = (float) imageHeight / (float) imageWidth;
        final int result = (int) (aspectRatioFactor * (float) parentWidth);
        return result;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh)
    {
        super.onSizeChanged(width, height, oldw, oldh);

        int iconSize = (int) (width * iconRelativeSize);
        iconBounds.set((width >> 1) - (iconSize >> 1), (height >> 1) - (iconSize >> 1), (width >> 1) + (iconSize >> 1), (height >> 1) + (iconSize >> 1));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (svg != null)
        {
            canvas.save();
            canvas.translate(iconBounds.left, iconBounds.top);
            svg.setDocumentWidth(iconBounds.width());
            svg.setDocumentHeight(iconBounds.height());
            svg.renderToCanvas(canvas);
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        attachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        attachedToWindow = false;
        super.onDetachedFromWindow();
    }
}
