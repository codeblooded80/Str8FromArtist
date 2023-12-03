package com.mh.str8fromartist_v3;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mh.str8fromartist_v3.mail.Email;
import com.mh.str8fromartist_v3.mail.EmailManager;

import java.util.List;

public class EnthusiastActivity extends AppCompatActivity implements BarcodeCallback {
    private DecoratedBarcodeView qrCodeView;
    private Button scanButton;
    private ImageView imageView;
    private TextView infoText;
    private TextView scanResultText;
    private MediaPlayer mediaPlayer;
    private BeepManager beepManager;
    private String scanResult;
    private Button shareFeedbackButton;
    private ImageButton thumbsUpButton;
    private EmailManager emailManager;
    private boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enthusiast);

        beepManager = new BeepManager(this);

        qrCodeView = findViewById(R.id.qrcodeScanner);

        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(view -> {

            qrCodeView.pause();
            // Start the scanning process
            imageView.setVisibility(View.GONE);
            scanResultText.setVisibility(View.GONE);
            infoText.setVisibility(View.GONE);
            thumbsUpButton.setVisibility(View.GONE);
            shareFeedbackButton.setVisibility(View.GONE);
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

        shareFeedbackButton = findViewById(R.id.shareFeedbackButton);
        handleShareFeedback();
        thumbsUpButton = findViewById(R.id.thumbsUpButton);

        thumbsUpHandler();
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        // Handle the scan result here
        scanResult = result.getText();
        beepManager.playBeepSoundAndVibrate();
        matchAndDisplayImage();
    }

    private void matchAndDisplayImage() {
        String imgName = "";
        String picName;

        switch (scanResult) {
            case "Pic1":
                imgName = "love_yourself";
                picName = "Love Yourself!";
                break;

            case "Pic2":
                imgName = "lavender_field";
                picName = "Lavender Field";
                break;

            case "Pic3":
                imgName = "humming_bird";
                picName = "A Humming Bird";
                break;

            case "Pic4":
                imgName = "ladybug_leaf";
                picName = "A Ladybug on a leaf";
                break;

            default:
                imgName = "love_yourself";
                picName = "";
                break;
        }
        showScanResultText(picName);

        qrCodeView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        if (imgName != null) {
            imageView.setImageDrawable(getImageResources(imgName));
        } else {
            // Display a placeholder image if the file couldn't be loaded.
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        thumbsUpButton.setVisibility(View.VISIBLE);
        shareFeedbackButton.setVisibility(View.VISIBLE);
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
                //System.out.println("X, Y: " + x + ", " + y);

                int width = view.getWidth();
                int height = view.getHeight();

                int part = getPart((int)x,(int)y,width, height);
                //System.out.println("part: "+part);
                String quadrant = "Quadrant "+part;
                //System.out.println("quadrant: "+quadrant);

                String audioPath;

                if (scanResult.equals("Pic1"))
                    audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic1-quadrant"+part+".wav" ;
                else if (scanResult.equals("Pic2"))
                    audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic2-quadrant"+part+".wav";
                else if (scanResult.equals("Pic3"))
                    audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic3-quadrant"+part+".wav";
                else
                    audioPath = "/storage/emulated/0/EasyVoiceRecorder/Pic4-quadrant"+part+".wav";

            /*if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
                }*/

                // Display the quadrant in a Toast
                showQuadrantToast(quadrant);
                playAudio(audioPath);
            }
            return true; // Return true to consume the touch event.
        });
    }

    private int getPart(int x, int y, int width, int height) {
        int partWidth = width/3;
        int partHeight = height/4;

        int column= x / partWidth;
        int row = y / partHeight;

        return ((row*3) + column + 1);
    }

    private void thumbsUpHandler() {
        thumbsUpButton.setOnClickListener(v -> toggleLikeState());
    }

    private void toggleLikeState() {
        isLiked = !isLiked;

        if (isLiked) {
            thumbsUpButton.setBackgroundResource(R.drawable.thumbs);
            // Handle actions when the picture is liked
        } else {
            thumbsUpButton.setBackgroundResource(R.drawable.ic_thumbs_up);
            // Handle actions when the like is removed
        }
    }

    private void handleShareFeedback(){
        shareFeedbackButton.setOnClickListener(v -> showPopupDialog());
    }

    public void showPopupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.feedback_popup, null);

        EditText editText = dialogView.findViewById(R.id.editText);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        builder.setView(dialogView);
        builder.setTitle("Your valuable feedback!");

        final AlertDialog dialog = builder.create();

        submitButton.setOnClickListener(view -> {
            String comment = editText.getText().toString();
            // Handle the submission of the comment here
            System.out.println("Comment: "+comment);
            Email email = new Email();
            email.setSubject("You've got feedback!");
            email.setBody(comment);
            if(null == emailManager) {
                emailManager = new EmailManager();
            }
            emailManager.sendEmail(email);
            Toast.makeText(this, "Feedback sent successfully", Toast.LENGTH_SHORT).show();

            // Close the dialog
            dialog.dismiss();
        });

        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle the cancel button click if needed
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private Drawable getImageResources(String imgName) {
        // Assuming 'context' is the current context (e.g., an Activity or Application context)
        Resources res = this.getResources();
        int resourceId = res.getIdentifier(imgName, "drawable", this.getPackageName());

    // Now, you can use the resource ID to access the Drawable of the image
        Drawable drawable = res.getDrawable(resourceId);

        return drawable;
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
        if(null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
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
