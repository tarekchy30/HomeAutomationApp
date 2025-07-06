package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    ListView pdfList;
    ArrayList<String> pdfNames = new ArrayList<>();
    ArrayList<String> pdfUrls = new ArrayList<>();

    String folderId = "1zqLVGNngMGZNDrinVJQkCQ5ystU-N4V3";
    String apiKey = "AIzaSyDCGxw1XWdVzRh9hKAFbQgRatgz1FhzykM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfList = findViewById(R.id.pdfList);
        fetchPdfList();
    }

    private void fetchPdfList() {
        String url = "https://www.googleapis.com/drive/v3/files?q='" + folderId + "'+in+parents+and+mimeType='application/pdf'&key=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray files = response.getJSONArray("files");

                        for (int i = 0; i < files.length(); i++) {
                            JSONObject file = files.getJSONObject(i);
                            String name = file.getString("name");
                            String id = file.getString("id");

                            pdfNames.add(name);
                            pdfUrls.add("https://drive.google.com/uc?export=download&id=" + id);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, pdfNames);
                        pdfList.setAdapter(adapter);

                        pdfList.setOnItemClickListener((parent, view, position, id) -> {
                            Intent intent = new Intent(MainActivity.this, PdfViewerActivity.class);
                            intent.putExtra("url", pdfUrls.get(position));
                            startActivity(intent);
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Error loading PDFs", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}

