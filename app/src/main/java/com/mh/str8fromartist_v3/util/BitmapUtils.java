package com.mh.str8fromartist_v3.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class BitmapUtils {

    public interface IconClickListener {
        void onMicrophoneClick(int quadrant);
        void onPlayClick(int quadrant);
        // Add more methods for other icons if needed
    }
    public static Bitmap divideIntoQuadrants(Bitmap originalBitmap, int divisions, Drawable microphoneIcon, Drawable playIcon, IconClickListener listener) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        Bitmap dividedBitmap = Bitmap.createBitmap(width, height, originalBitmap.getConfig());
        Canvas canvas = new Canvas(dividedBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));

        int quadrantWidth = width / divisions;
        int quadrantHeight = height / divisions;

        // Draw dashed lines to divide the image into quadrants
        for (int i = 1; i < divisions; i++) {
            int x = i * quadrantWidth;
            int y = i * quadrantHeight;

            // Draw horizontal dashed line
            canvas.drawLine(0, y, width, y, paint);

            // Draw vertical dashed line
            canvas.drawLine(x, 0, x, height, paint);

        }

        for (int i = 1; i < 5; i++) {
            int x = i * quadrantWidth;
            int y = i * quadrantHeight;

            // Draw horizontal dashed line
            canvas.drawLine(0, y, width, y, paint);

            // Draw vertical dashed line
            canvas.drawLine(x, 0, x, height, paint);

            // Calculate center of the quadrant
            int centerX = i * quadrantWidth - quadrantWidth / 2;
            int centerY = i * quadrantHeight - quadrantHeight / 2;

            // Draw microphone icon
            int microphoneIconSize = Math.min(quadrantWidth, quadrantHeight) / 3; // Adjust the divisor to control the size
            int microphoneIconLeft = centerX - (microphoneIconSize * 3) / 4; // Adjust the numerator to control the spacing
            int microphoneIconTop = centerY - microphoneIconSize / 2;
            int microphoneIconRight = microphoneIconLeft + microphoneIconSize;
            int microphoneIconBottom = microphoneIconTop + microphoneIconSize;

            microphoneIcon.setBounds(microphoneIconLeft, microphoneIconTop, microphoneIconRight, microphoneIconBottom);
            microphoneIcon.draw(canvas);

            // Draw play icon next to the microphone icon
            int playIconSize = Math.min(quadrantWidth, quadrantHeight) / 3; // Adjust the divisor to control the size
            int playIconLeft = centerX + (playIconSize * 1) / 4; // Adjust the numerator to control the spacing
            int playIconTop = centerY - playIconSize / 2;
            int playIconRight = playIconLeft + playIconSize;
            int playIconBottom = playIconTop + playIconSize;

            playIcon.setBounds(playIconLeft, playIconTop, playIconRight, playIconBottom);
            playIcon.draw(canvas);

            final int quadrantIndex = i; // final is needed to use it inside the onClick listener

            // Set click listeners for the icons
            if (listener != null) {
                final int finalQuadrantIndex = quadrantIndex;

                // Set click listener for the microphone icon
                canvas.setBitmap(dividedBitmap);
                canvas.clipRect(microphoneIconLeft, microphoneIconTop, playIconRight, playIconBottom);
                microphoneIcon.draw(canvas);

                // Handle click event for the microphone icon
                microphoneIcon.setCallback(new Drawable.Callback() {
                    @Override
                    public void invalidateDrawable(Drawable who) {
                        // Not needed
                    }

                    @Override
                    public void scheduleDrawable(Drawable who, Runnable what, long when) {
                        // Not needed
                    }

                    @Override
                    public void unscheduleDrawable(Drawable who, Runnable what) {
                        // Not needed
                    }
                });
                microphoneIcon.setVisible(true, true);

                microphoneIcon.setCallback(null);

                // Set click listener for the play icon
                canvas.setBitmap(dividedBitmap);
                canvas.clipRect(playIconLeft, playIconTop, playIconRight, playIconBottom);
                playIcon.draw(canvas);

                // Handle click event for the play icon
                playIcon.setCallback(new Drawable.Callback() {
                    @Override
                    public void invalidateDrawable(Drawable who) {
                        // Not needed
                    }

                    @Override
                    public void scheduleDrawable(Drawable who, Runnable what, long when) {
                        // Not needed
                    }

                    @Override
                    public void unscheduleDrawable(Drawable who, Runnable what) {
                        // Not needed
                    }
                });
                playIcon.setVisible(true, true);

                playIcon.setCallback(null);
            }
        }

        return dividedBitmap;
    }
}
