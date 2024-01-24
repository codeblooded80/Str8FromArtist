package com.mh.str8fromartist_v3.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DashedLinesView extends View {

    private Paint paint;

    public DashedLinesView(Context context) {
        super(context);
        init();
    }

    public DashedLinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw vertical dashed line
        canvas.drawLine(width / 2, 0, width / 2, height, paint);

        // Draw horizontal dashed line
        canvas.drawLine(0, height / 2, width, height / 2, paint);
    }
}