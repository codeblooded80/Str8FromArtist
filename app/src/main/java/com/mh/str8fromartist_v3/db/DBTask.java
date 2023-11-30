package com.mh.str8fromartist_v3.db;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";

        String jdbcUrl = "";
        String user = "";
        String password = "";

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

            // Perform a simple query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM artwork");

            // Process the results
            while (resultSet.next()) {
                result += resultSet.getString("art_name") + "\n";
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error: " + e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the result on the UI thread (update UI, show a toast, etc.)
        // For simplicity, we'll print the result to the console
        System.out.println(result);
    }
}