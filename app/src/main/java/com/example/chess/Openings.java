package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;


public class Openings extends AppCompatActivity {

    public String opDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openings);
    }

    public void switch_to_opening_info(View view) {
        int id = view.getId() ;
        String fileName = getResources().getResourceEntryName(id);
        opDetails = readFile(getApplicationContext(),fileName);
        Intent intent = new Intent(Openings.this, OpeningDetails.class);
        intent.putExtra("opDetails", opDetails);
        startActivity(intent);
    }


    public String readFile(Context context, String fileName) {
        StringBuilder content = new StringBuilder();
        Resources resources = context.getResources();

        // Get the resource identifier dynamically
        int resourceId = resources.getIdentifier(fileName, "raw", context.getPackageName());

        if (resourceId == 0) {
            // Resource not found
            return null;
        }

        try (InputStream is = resources.openRawResource(resourceId);
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}