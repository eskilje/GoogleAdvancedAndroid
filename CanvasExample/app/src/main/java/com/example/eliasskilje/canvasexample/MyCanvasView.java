package com.example.eliasskilje.canvasexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvasView extends View{
    private Paint paint;
    private Path path;
    private int drawColor;
    private int backgroundColor;
    private Canvas extraCanvas;
    private Bitmap extraBitmap;
    private float mX, mY;
    private Rect frame;

    private static final float TOUCH_TOLERANCE = 4;



    MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context);

        backgroundColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_orange, null);
        drawColor = ResourcesCompat.getColor(getResources(),
                R.color.opaque_yellow, null);

        // Holds the path we are currently drawing.
        path = new Path();
        // Set up the paint with which to draw.
        paint = new Paint();
        paint.setColor(drawColor);
        // Smoothes out edges of what is drawn without affecting shape.
        paint.setAntiAlias(true);
        // Dithering affects how colors with higher-precision device
        // than the are down-sampled.
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE); // default: FILL
        paint.setStrokeJoin(Paint.Join.ROUND); // default: MITER
        paint.setStrokeCap(Paint.Cap.ROUND); // default: BUTT
        paint.setStrokeWidth(12); // default: Hairline-width (really thin)
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            // Reset mX and mY to the last drawn point.
            mX = x;
            mY = y;
            // Save the path in the extra bitmap,
            // which we access through its canvas.
            extraCanvas.drawPath(path, paint);
        }
    }

    private void touchUp() {
        // Reset the path so it doesn't get drawn again.
        path.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Invalidate() is inside the case statements because there are many
        // other types of motion events passed into this listener,
        // and we don't want to invalidate the view for those.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                // No need to invalidate because we are not drawing anything.
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                // No need to invalidate because we are not drawing anything.
                break;
            default:
                // Do nothing.
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the bitmap that stores the path the user has drawn.
        // Initially the user has not drawn anything
        // so we see only the colored bitmap.
        canvas.drawBitmap(extraBitmap, 0, 0, null);
        canvas.drawRect(frame, paint);

    }

    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // Create bitmap, create canvas with bitmap, fill canvas with color.
        extraBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        extraCanvas = new Canvas(extraBitmap);
        // Fill the Bitmap with the background color.
        extraCanvas.drawColor(backgroundColor);
        int inset = 40;
        frame = new Rect (inset, inset, width - inset, height - inset);
    }
}
