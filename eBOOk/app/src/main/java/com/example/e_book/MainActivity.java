package com.example.e_book;

import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window  = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_primary));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> pdfFiles = getPdfFilesFromAssets();
        PdfAdapter adapter = new PdfAdapter(pdfFiles);
        recyclerView.setAdapter(adapter);


    }

    private List<String> getPdfFilesFromAssets() {

        List<String> pdfFiles = new ArrayList<>();

        try {
            String[] files = getAssets().list("");
            if(files != null){
                for(String file : files){
                    if(file.toLowerCase().endsWith(".pdf")){
                        pdfFiles.add(file);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return pdfFiles;

    }
}