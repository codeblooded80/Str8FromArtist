package com.mh.str8fromartist_v3;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mh.str8fromartist_v3.db.DBTask;

public class ArtistActivity extends AppCompatActivity {

    private TextView simpleText;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        simpleText = findViewById(R.id.simpleText);

        tableLayout = findViewById(R.id.tableLayout);
        loadData2Table();

        //new DBTask().execute();

    }

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
