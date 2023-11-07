package com.mh.str8fromartist;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private MediaPlayer mediaPlayer;
    private Button playButton;

    private BeepManager beepManager;

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
            qrCodeView.setVisibility(View.VISIBLE);
            qrCodeView.initializeFromIntent(getIntent());
            qrCodeView.decodeSingle(this);
            qrCodeView.resume();
        });

        imageView = findViewById(R.id.imageView);

        playButton = findViewById(R.id.playButton);


    }

    private void findAndDisplayImage(String scanResult) {
        String imagePath;
        String audioPath = null;

        switch (scanResult) {
            case "Pic1":
                imagePath = "/storage/emulated/0/Download/PHOTO-2023-10-30-16-44-09.jpg";
                audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic1.wav";
                break;

            case "Pic2":
                imagePath = "/storage/emulated/0/Download/PHOTO-2023-10-30-16-44-10.jpg";
                audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic2.wav";
                break;

            case "Pic3":
                imagePath = "/storage/emulated/0/Download/PHOTO-2023-10-30-16-44-11.jpg";
                audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic3.wav";
                break;

            case "Pic4":
                imagePath = "/storage/emulated/0/Download/1699342670779_PHOTO-2023-10-30-16-44-09.jpg";
                audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic4.wav";
                break;

            default:
                imagePath = "/storage/emulated/0/DCIM/Restored/IMG_4397.JPG";
                audioPath = "/storage/emulated/0/EasyVoiceRecorder/DefaultPic.wav";
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

        playAudio(audioPath);
    }

    private void playAudio(String audioPath) {
        try {
            // Create and initialize the MediaPlayer.
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();

            playButton.setOnClickListener(v -> {
                /*if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // Start playing the audio.
                    playButton.setText("Pause Audio");
                } else {
                    mediaPlayer.pause(); // Pause the audio.
                    playButton.setText("Play Audio");
                }*/
                mediaPlayer.start();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        // Handle the scan result here
        String scanResult = result.getText();
        System.out.println("Scan Result: " + scanResult);
        showToast("QR Code Scanned: " + scanResult);
        beepManager.playBeepSoundAndVibrate();
        findAndDisplayImage(scanResult);
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {
        // Handle possible result points if needed
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

