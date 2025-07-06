package com.example.mybook;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
        String pdfFileName = getIntent().getStringExtra("pdf_name");

        try {
            InputStream inputStream = getAssets().open(pdfFileName);
            pdfView.fromStream(inputStream)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }
}
