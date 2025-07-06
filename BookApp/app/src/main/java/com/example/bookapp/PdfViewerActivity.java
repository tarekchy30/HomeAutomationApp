package com.example.bookapp;

import static android.content.Intent.getIntent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;


import com.example.bookapp.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



import okhttp3.OkHttpClient;

public class PdfViewerActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);

        String url = getIntent().getStringExtra("url");
        downloadAndDisplayPdf(url);
    }

    private void downloadAndDisplayPdf(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                File file = File.createTempFile("temp", ".pdf", getCacheDir());
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(response.body().bytes());
                fos.close();

                runOnUiThread(() -> pdfView.fromFile(file).load());
            }
        });
    }
}
