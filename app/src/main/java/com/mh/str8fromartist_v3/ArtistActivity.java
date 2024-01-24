package com.mh.str8fromartist_v3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArtistActivity extends AppCompatActivity {

    private TextView simpleText;
    private TableLayout tableLayout;

    private static final int REQUEST_PERMISSION_CODE = 1000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String audioFilePath;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private TextView audioRecStatus;
    private TextView audioRecTime;
    private long startTimeMillis;
    private AlertDialog recordingDialog;
    private ImageView capImageView;
    private String currentPhotoPath;

    private Button btnRecordQ1;
    private Button btnRecordQ2;
    private Button btnRecordQ3;
    private Button btnRecordQ4;
    private LinearLayout audioLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        simpleText = findViewById(R.id.simpleText);

        tableLayout = findViewById(R.id.tableLayout);
        audioLayout = findViewById(R.id.audioLayout);
        loadData2Table();

//        setupAudioRecording();
    }

    private void setupAudioRecording(String artFilename) {
        btnRecordQ1 = findViewById(R.id.btnRecordQ1);
        audioRecTime = findViewById(R.id.audioRecTime);

        btnRecordQ1.setOnClickListener(v -> {
            startRecording(artFilename+"-q1");
            showRecordingDialog("Q1");
        });

        btnRecordQ2 = findViewById(R.id.btnRecordQ2);
        audioRecTime = findViewById(R.id.audioRecTime);

        btnRecordQ2.setOnClickListener(v -> {
            startRecording(artFilename+"-q2");
            showRecordingDialog("Q2");
        });

        btnRecordQ3 = findViewById(R.id.btnRecordQ3);
        audioRecTime = findViewById(R.id.audioRecTime);

        btnRecordQ3.setOnClickListener(v -> {
            startRecording(artFilename+"-q3");
            showRecordingDialog("Q3");
        });

        btnRecordQ4 = findViewById(R.id.btnRecordQ4);
        audioRecTime = findViewById(R.id.audioRecTime);

        btnRecordQ4.setOnClickListener(v -> {
            startRecording(artFilename+"-q4");
            showRecordingDialog("Q4");
        });
    }

    private void startRecording(String quadrant) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

//        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp";

        File audioDirectory = new File(getExternalFilesDir(null), "audio");
        if (!audioDirectory.exists()) {
            audioDirectory.mkdirs();
        }
        audioFilePath = audioDirectory.getAbsolutePath()+"/"+quadrant+".3gp";
        System.out.println("Audio File Path: "+audioFilePath);

        mediaRecorder.setOutputFile(audioFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void showRecordingDialog(String quadrant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Audio for "+quadrant);
        builder.setMessage("Recording in progress...");
        builder.setCancelable(false);

        builder.setPositiveButton("Stop & Save Recording", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                stopRecording();
                dialog.dismiss();
            }
        });

        // Set the layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_recording, null);
        builder.setView(dialogView);

        audioRecTime = dialogView.findViewById(R.id.audioRecTime);

        recordingDialog = builder.create();
        recordingDialog.show();

        // Start updating the recording time
        startTimeMillis = System.currentTimeMillis();
        startRecordingTimeUpdater();
    }

    private void startRecordingTimeUpdater() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
                updateRecordingTime(elapsedTimeMillis);
                handler.postDelayed(this, 1000); // Update every second
            }
        });
    }

    private void updateRecordingTime(long elapsedTimeMillis) {
        long elapsedSeconds = elapsedTimeMillis / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;

        String timeString = String.format("%02d:%02d", minutes, seconds);
        audioRecTime.setText("Recording Time: " + timeString);
    }


    private void startPlayback() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRecording();
        stopPlayback();
    }


    //

    public void captureImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.mh.str8fromartist_v3.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

            System.out.println("File stored in:" +photoFile);
           // startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");*/

            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);

            capImageView = findViewById(R.id.capImageView);
            capImageView.setImageBitmap(imageBitmap);
            capImageView.setVisibility(View.VISIBLE);

            showFilenameInputDialog(imageBitmap);

            /*Drawable microphoneIcon = getResources().getDrawable(R.drawable.microphone_icon);
            Drawable playIcon = getResources().getDrawable(R.drawable.play_icon);

            // Divide the image into quadrants with dashed lines
            capImageView.post(() -> {
                Bitmap dividedBitmap = BitmapUtils.divideIntoQuadrants(imageBitmap, 2, microphoneIcon, playIcon, new BitmapUtils.IconClickListener() {
                    @Override
                    public void onMicrophoneClick(int quadrant) {
                        // Handle microphone click for the specific quadrant
                        Toast.makeText(ArtistActivity.this, "Microphone clicked in quadrant " + quadrant, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPlayClick(int quadrant) {
                        // Handle play click for the specific quadrant
                        Toast.makeText(ArtistActivity.this, "Play clicked in quadrant " + quadrant, Toast.LENGTH_SHORT).show();
                    }

                    // Add more methods for other icons if needed
                });
                capImageView.setImageBitmap(dividedBitmap);
            });

            touchImageQuadrantAndPlayAudio();*/
            audioLayout.setVisibility(View.VISIBLE);
        }
    }

    private void showFilenameInputDialog(Bitmap imageBitmap) {
        // Create a custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filename_input_dialog, null);

        // Find the EditText in the custom layout
        EditText filenameEditText = dialogView.findViewById(R.id.filenameEditText);

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Enter Filename")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Get the filename entered by the user
                    String newFilename = filenameEditText.getText().toString().trim();

                    if (newFilename.isEmpty()) {
                        Toast.makeText(this, "Please enter a filename", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    File directory = new File(getExternalFilesDir(null), "images");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File oFile  = new File(currentPhotoPath);
                    File nFile = new File(directory, newFilename + ".jpg");
                    System.out.println("New File: "+nFile);

                    // Rename the file to the desired filename
                    if (oFile.exists() && oFile.renameTo(nFile)) {
                        Toast.makeText(this, "Image saved as: " + newFilename, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                    }

                    setupAudioRecording(newFilename);
                })
                .setNegativeButton("Cancel", null);

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        System.out.println("Image Path: "+currentPhotoPath);
        return image;
    }

    private void touchImageQuadrantAndPlayAudio() {

        capImageView.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float x = event.getX();
                float y = event.getY();
                System.out.println("X, Y: " + x + ", " + y);

                float centerX = view.getWidth() / 2f;
                float centerY = view.getHeight() / 2f;

                String quadrant;

                if (x < centerX && y < centerY) {
                    quadrant = "Top Left Quadrant";
                } else if (x >= centerX && y < centerY) {
                    quadrant = "Top Right Quadrant";

                } else if (x < centerX && y >= centerY) {
                    quadrant = "Bottom Left Quadrant";

                } else {
                    quadrant = "Bottom Right Quadrant";

                }
                startRecording("");
                showRecordingDialog(quadrant);
            }
            return true; // Return true to consume the touch event.
        });
    }

    //

    public void loadData2Table() {
        // Create a new row to be added
        TableRow row1 = new TableRow(this);

        // Create TextViews to hold data
        TextView data11 = new TextView(this);
        data11.setBackgroundResource(R.drawable.table_cell_border);
        data11.setText("Love Yourself");
        data11.setPadding(8, 8, 8, 8);

        TextView data12 = new TextView(this);
        data12.setBackgroundResource(R.drawable.table_cell_border);
        data12.setText("12");
        data12.setPadding(8, 8, 8, 8);

        TextView data13 = new TextView(this);
        data13.setBackgroundResource(R.drawable.table_cell_border);
        data13.setText("Top Right");
        data13.setPadding(8, 8, 8, 8);

        TextView data14 = new TextView(this);
        data14.setBackgroundResource(R.drawable.table_cell_border);
        data14.setText("5");
        data14.setPadding(8, 8, 8, 8);

        TextView data15 = new TextView(this);
        data15.setBackgroundResource(R.drawable.table_cell_border);
        data15.setText("4");
        data15.setPadding(8, 8, 8, 8);

        // Add TextViews to the row
        row1.addView(data11);
        row1.addView(data12);
        row1.addView(data13);
        row1.addView(data14);
        row1.addView(data15);

        // Add the row to the table
        tableLayout.addView(row1);

        // You can add more data and customize as needed

        // Create a new row to be added
        TableRow row2 = new TableRow(this);

        // Create TextViews to hold data
        TextView data21 = new TextView(this);
        data21.setBackgroundResource(R.drawable.table_cell_border);
        data21.setText("Lavender Field");
        data21.setPadding(8, 8, 8, 8);

        TextView data22 = new TextView(this);
        data22.setBackgroundResource(R.drawable.table_cell_border);
        data22.setText("8");
        data22.setPadding(8, 8, 8, 8);

        TextView data23 = new TextView(this);
        data23.setBackgroundResource(R.drawable.table_cell_border);
        data23.setText("Top Left");
        data23.setPadding(8, 8, 8, 8);

        TextView data24 = new TextView(this);
        data24.setBackgroundResource(R.drawable.table_cell_border);
        data24.setText("6");
        data24.setPadding(8, 8, 8, 8);

        TextView data25 = new TextView(this);
        data25.setBackgroundResource(R.drawable.table_cell_border);
        data25.setText("5");
        data25.setPadding(8, 8, 8, 8);

        // Add TextViews to the row
        row2.addView(data21);
        row2.addView(data22);
        row2.addView(data23);
        row2.addView(data24);
        row2.addView(data25);

        // Add the row to the table
        tableLayout.addView(row2);

        // Create a new row to be added
        TableRow row3 = new TableRow(this);

        // Create TextViews to hold data
        TextView data31 = new TextView(this);
        data31.setBackgroundResource(R.drawable.table_cell_border);
        data31.setText("Humming Bird");
        data31.setPadding(8, 8, 8, 8);

        TextView data32 = new TextView(this);
        data32.setBackgroundResource(R.drawable.table_cell_border);
        data32.setText("23");
        data32.setPadding(8, 8, 8, 8);

        TextView data33 = new TextView(this);
        data33.setBackgroundResource(R.drawable.table_cell_border);
        data33.setText("Bottom Right");
        data33.setPadding(8, 8, 8, 8);

        TextView data34 = new TextView(this);
        data34.setBackgroundResource(R.drawable.table_cell_border);
        data34.setText("18");
        data34.setPadding(8, 8, 8, 8);

        TextView data35 = new TextView(this);
        data35.setBackgroundResource(R.drawable.table_cell_border);
        data35.setText("14");
        data35.setPadding(8, 8, 8, 8);

        // Add TextViews to the row
        row3.addView(data31);
        row3.addView(data32);
        row3.addView(data33);
        row3.addView(data34);
        row3.addView(data35);

        // Add the row to the table
        tableLayout.addView(row3);


        // Create a new row to be added
        TableRow row4 = new TableRow(this);

        // Create TextViews to hold data
        TextView data41 = new TextView(this);
        data41.setBackgroundResource(R.drawable.table_cell_border);
        data41.setText("Ladybug");
        data41.setPadding(8, 8, 8, 8);

        TextView data42 = new TextView(this);
        data42.setBackgroundResource(R.drawable.table_cell_border);
        data42.setText("19");
        data42.setPadding(8, 8, 8, 8);

        TextView data43 = new TextView(this);
        data43.setBackgroundResource(R.drawable.table_cell_border);
        data43.setText("Bottom Left");
        data43.setPadding(8, 8, 8, 8);

        TextView data44 = new TextView(this);
        data44.setBackgroundResource(R.drawable.table_cell_border);
        data44.setText("12");
        data44.setPadding(8, 8, 8, 8);

        TextView data45 = new TextView(this);
        data45.setBackgroundResource(R.drawable.table_cell_border);
        data45.setText("8");
        data45.setPadding(8, 8, 8, 8);

        // Add TextViews to the row
        row4.addView(data41);
        row4.addView(data42);
        row4.addView(data43);
        row4.addView(data44);
        row4.addView(data45);

        // Add the row to the table
        tableLayout.addView(row4);
    }
}
