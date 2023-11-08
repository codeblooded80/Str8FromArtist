package com.mh.str8fromartist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BarcodeCallback {
    private DecoratedBarcodeView qrCodeView;
    private Button scanButton;
    private ImageView imageView;
    private TextView infoText;
    private TextView scanResultText;
    private MediaPlayer mediaPlayer;
    private BeepManager beepManager;
    private String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beepManager = new BeepManager(this);

        qrCodeView = findViewById(R.id.qrcodeScanner);

        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(view -> {

            qrCodeView.pause();
            // Start the scanning process
            imageView.setVisibility(View.GONE);
            scanResultText.setVisibility(View.GONE);
            infoText.setVisibility(View.GONE);
            if (null != mediaPlayer) {
                mediaPlayer.stop();
            }
            qrCodeView.setVisibility(View.VISIBLE);
            qrCodeView.initializeFromIntent(getIntent());
            qrCodeView.decodeSingle(this);
            qrCodeView.resume();
        });

        imageView = findViewById(R.id.imageView);

        scanResultText = findViewById(R.id.scanResultText);
        infoText = findViewById(R.id.infoText);

        touchImageQuadrantAndPlayAudio();

    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        // Handle the scan result here
        scanResult = result.getText();
        System.out.println("Scan Result: " + scanResult);
        showScanResultText("Match found: " + scanResult);
        beepManager.playBeepSoundAndVibrate();
        matchAndDisplayImage();
    }

    private void matchAndDisplayImage() {
        String imagePath;

        switch (scanResult) {
            case "Pic1":
                imagePath = "/storage/emulated/0/Download/PHOTO-2023-10-30-16-44-09.jpg";
                break;

            case "Pic2":
                imagePath = "/storage/emulated/0/Download/PHOTO-2023-10-30-16-44-10.jpg";
                break;

            case "Pic3":
                imagePath = "/storage/emulated/0/Download/PHOTO-2023-10-30-16-44-11.jpg";
                break;

            case "Pic4":
                imagePath = "/storage/emulated/0/Download/1699342670779_PHOTO-2023-10-30-16-44-09.jpg";
                break;

            default:
                imagePath = "/storage/emulated/0/DCIM/Restored/IMG_4397.JPG";
                break;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        qrCodeView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            // Display a placeholder image if the file couldn't be loaded.
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    private void playAudio(String audioPath) {
        try {
            if (null != mediaPlayer) {
                mediaPlayer.stop();
            }
            // Create and initialize the MediaPlayer.
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void touchImageQuadrantAndPlayAudio() {

        imageView.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float x = event.getX();
                float y = event.getY();
                System.out.println("X, Y: " + x + ", " + y);

                float centerX = view.getWidth() / 2f;
                float centerY = view.getHeight() / 2f;

                String quadrant;
                String audioPath;

                if (x < centerX && y < centerY) {
                    quadrant = "Top Left Quadrant";
                    if (scanResult.equals("Pic1"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic1-quadrant1.wav";
                    else if (scanResult.equals("Pic2"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic2-quadrant1.wav";
                    else if (scanResult.equals("Pic3"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic3-quadrant1.wav";
                    else
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic4-quadrant1.wav";
                } else if (x >= centerX && y < centerY) {
                    quadrant = "Top Right Quadrant";
                    if (scanResult.equals("Pic1"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic1-quadrant2.wav";
                    else if (scanResult.equals("Pic2"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic2-quadrant2.wav";
                    else if (scanResult.equals("Pic3"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic3-quadrant2.wav";
                    else
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic4-quadrant2.wav";
                } else if (x < centerX && y >= centerY) {
                    quadrant = "Bottom Left Quadrant";
                    if (scanResult.equals("Pic1"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic1-quadrant3.wav";
                    else if (scanResult.equals("Pic2"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic2-quadrant3.wav";
                    else if (scanResult.equals("Pic3"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic3-quadrant3.wav";
                    else
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic4-quadrant3.wav";
                } else {
                    quadrant = "Bottom Right Quadrant";
                    if (scanResult.equals("Pic1"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic1-quadrant4.wav";
                    else if (scanResult.equals("Pic2"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic2-quadrant4.wav";
                    else if (scanResult.equals("Pic3"))
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic3-quadrant4.wav";
                    else
                        audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic4-quadrant4.wav";
                }

                // Display the quadrant in a Toast
                showQuadrantToast(quadrant);
                playAudio(audioPath);
            }
            return true; // Return true to consume the touch event.
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {
        // Handle possible result points if needed
    }

    private void showQuadrantToast(String message) {
        Toast.makeText(this, "Touched " + message, Toast.LENGTH_SHORT).show();
    }

    private void showScanResultText(String message) {
        scanResultText.setVisibility(View.VISIBLE);
        infoText.setVisibility(View.VISIBLE);
        scanResultText.setText(message);
    }
}

