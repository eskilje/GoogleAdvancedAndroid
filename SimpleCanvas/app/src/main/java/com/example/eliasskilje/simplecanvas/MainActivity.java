package com.example.eliasskilje.simplecanvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas canvas;
    private Paint paint;
    private Paint paintText;
    private Bitmap bitmap;
    private ImageView imageView;
    private Rect rect;
    private Rect bounds;

    private static final int OFFSET = 120;
    private int offset = OFFSET;

    private static final int MULTIPLIER = 100;

    private int colorBackground;
    private int colorRectangle;
    private int colorAccent;

    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;
        if(offset == OFFSET) {
            bitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            imageView.setImageBitmap(bitmap);
            canvas = new Canvas(bitmap);
            canvas.drawColor(colorBackground);
            canvas.drawText(getString(R.string.keep_tapping), 100, 100, paintText);
            offset += OFFSET;
        }
        else {
            if (offset < halfWidth && offset < halfHeight) {
                // Change the color by subtracting an integer.
                paint.setColor(colorRectangle - MULTIPLIER*offset);
                rect.set(
                        offset, offset, vWidth - offset, vHeight - offset);
                canvas.drawRect(rect, paint);
                // Increase the indent.
                offset += OFFSET;
            }
            else {
                paint.setColor(colorAccent);
                canvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, paint);
                String text = getString(R.string.done);
                // Get bounding box for text to calculate where to draw it.
                paintText.getTextBounds(text, 0, text.length(), bounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - bounds.centerX();
                int y = halfHeight - bounds.centerY();
                canvas.drawText(text, x, y, paintText);
            }
        }
        view.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paint = new Paint();
        paintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
        rect = new Rect();
        bounds = new Rect();

        colorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        colorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        colorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        paint.setColor(colorBackground);
        paintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        paintText.setTextSize(70);
        imageView = findViewById(R.id.myimageview);

    }
}
