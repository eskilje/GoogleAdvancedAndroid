package com.example.eliasskilje.clippingexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ClippedView extends View {
    private Paint paint;
    private Path path;

    private int mClipRectRight =
            (int) getResources().getDimension(R.dimen.clipRectRight);
    private int mClipRectBottom =
            (int) getResources().getDimension(R.dimen.clipRectBottom);
    private int mClipRectTop =
            (int) getResources().getDimension(R.dimen.clipRectTop);
    private int mClipRectLeft =
            (int) getResources().getDimension(R.dimen.clipRectLeft);
    private int mRectInset =
            (int) getResources().getDimension(R.dimen.rectInset);
    private int mSmallRectOffset =
            (int) getResources().getDimension(R.dimen.smallRectOffset);

    private int mCircleRadius =
            (int) getResources().getDimension(R.dimen.circleRadius);

    private int mTextOffset =
            (int) getResources().getDimension(R.dimen.textOffset);
    private int mTextSize =
            (int) getResources().getDimension(R.dimen.textSize);

    private int mColumnOne = mRectInset;
    private int mColumnnTwo = mColumnOne + mRectInset + mClipRectRight;

    private int mRowOne = mRectInset;
    private int mRowTwo = mRowOne + mRectInset + mClipRectBottom;
    private int mRowThree = mRowTwo + mRectInset + mClipRectBottom;
    private int mRowFour = mRowThree + mRectInset + mClipRectBottom;
    private int mTextRow = mRowFour + (int)(1.5 * mClipRectBottom);

    private final RectF mRectF;

    private void drawClippedRectangle(Canvas canvas) {
        canvas.clipRect(mClipRectLeft, mClipRectTop,
                mClipRectRight, mClipRectBottom);
        canvas.drawColor(Color.WHITE);

        // Change the color to red and
        // draw a line inside the clipping rectangle.
        paint.setColor(Color.RED);
        canvas.drawLine(mClipRectLeft, mClipRectTop,
                mClipRectRight, mClipRectBottom, paint);

        // Set the color to green and
        // draw a circle inside the clipping rectangle.
        paint.setColor(Color.GREEN);
        canvas.drawCircle(mCircleRadius, mClipRectBottom - mCircleRadius,
                mCircleRadius, paint);

        // Set the color to blue and draw text aligned with the right edge
        // of the clipping rectangle.
        paint.setColor(Color.BLUE);
        // Align the RIGHT side of the text with the origin.
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(getContext().getString(R.string.clipping),
                mClipRectRight, mTextOffset, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        canvas.save();
        canvas.translate(mColumnOne, mRowOne);
        drawClippedRectangle(canvas);
        canvas.restore();
        // Draw text with a translate transformation applied.
        canvas.save();
        paint.setColor(Color.CYAN);
// Align the RIGHT side of the text with the origin.
        paint.setTextAlign(Paint.Align.LEFT);
// Apply transformation to canvas.
        canvas.translate(mColumnnTwo, mTextRow);
// Draw text.
        canvas.drawText(
                getContext().getString(R.string.translated), 0, 0, paint);
        canvas.restore();

// Draw text with a translate and skew transformations applied.
        canvas.save();
        paint.setTextSize(mTextSize);
        paint.setTextAlign(Paint.Align.RIGHT);
// Position text.
        canvas.translate(mColumnnTwo, mTextRow);
        // Apply skew transformation.
        canvas.skew(0.2f, 0.3f);
        canvas.drawText(
                getContext().getString(R.string.skewed), 0, 0, paint);
        canvas.restore();
    } // End of onDraw()

    public ClippedView(Context context) {
        this(context,null);

    }

    public ClippedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        paint = new Paint();
        // Smooth out edges of what is drawn without affecting shape.
        paint.setAntiAlias(true);
        paint.setStrokeWidth(
                (int) getResources().getDimension(R.dimen.strokeWidth));
        paint.setTextSize((int) getResources().getDimension(R.dimen.textSize));
        path = new Path();

        mRectF = new RectF(new Rect(mRectInset, mRectInset,
                mClipRectRight-mRectInset, mClipRectBottom-mRectInset));
    }
}
