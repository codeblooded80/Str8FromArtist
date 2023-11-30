package com.mh.str8fromartist_v3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnArtist = findViewById(R.id.btnArtist);
        Button btnEnthusiast = findViewById(R.id.btnEnthusiast);

        btnArtist.setOnClickListener(v -> {
            // Navigate to ArtistActivity
            Intent intent = new Intent(MainActivity.this, ArtistActivity.class);
            startActivity(intent);
        });

        btnEnthusiast.setOnClickListener(v -> {
            // Navigate to EnthusiastActivity
            Intent intent = new Intent(MainActivity.this, EnthusiastActivity.class);
            startActivity(intent);
        });
    }
}

