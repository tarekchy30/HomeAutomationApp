package com.example.e_book;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.IOException;
import java.io.InputStream;

public class PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);


        PDFView pdfView = findViewById(R.id.pdfView);
        String pdfFilename = getIntent().getStringExtra("pdf_name");

        try {
            InputStream inputStream = getAssets().open(pdfFilename);
            pdfView.fromStream(inputStream)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        }catch (IOException e){
            e.printStackTrace();
            finish();
        }



    }
}