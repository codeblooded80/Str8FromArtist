package com.mh.str8fromartist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
/*import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;*/
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class QRScannerActivity { /*extends AppCompatActivity implements SurfaceHolder.Callback {
    private SurfaceView cameraPreview;
    private ImageView mappedImage;
    private SurfaceHolder surfaceHolder;
    private MultiFormatReader qrCodeReader;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        cameraPreview = findViewById(R.id.cameraPreview);
        mappedImage = findViewById(R.id.mappedImage);
        surfaceHolder = cameraPreview.getHolder();

        // Initialize QR code reader
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.of(BarcodeFormat.QR_CODE));
        qrCodeReader = new MultiFormatReader();
        qrCodeReader.setHints(hints);
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCameraPreview(holder);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraPreview(surfaceHolder);
            } else {
                Log.e("QRScannerActivity", "Camera permission denied.");
            }
        }
    }

    private void startCameraPreview(SurfaceHolder holder) {
        try {
            qrCodeReader.setHints(null);
            qrCodeReader.setFormats(BarcodeFormat.QR_CODE);
            CameraManager manager = new CameraManager(getApplication());
            manager.openDriver(holder);
            manager.startPreview();
        } catch (IOException e) {
            Log.e("QRScannerActivity", "Error starting camera preview: " + e.getMessage());
        }
    }

    // Handle QR code scanning results and display the mapped image
    private void handleQRCode(Result result) {
        String qrCodeData = result.getText();
        String mappedImagePath = getImagePathForQRCode(qrCodeData);

        if (mappedImagePath != null) {
            mappedImage.setImageURI(Uri.parse(mappedImagePath));
            mappedImage.setVisibility(View.VISIBLE);
        }
    }

    private String getImagePathForQRCode(String qrCodeData) {
        // Implement your mapping logic here to retrieve the image path
        // based on the QR code data
        // This could involve querying a database or reading from a JSON file
        // Return the path to the image
        return null;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}

    // Handle scanning results when the QR code is detected
    public void handleDecode(Result result) {
        // Handle the decoded result here
        handleQRCode(result);
    }*/
}
