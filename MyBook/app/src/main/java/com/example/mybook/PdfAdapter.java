package com.example.mybook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
    private final List<String> pdfFiles;
    public PdfAdapter(List<String> pdfFiles) {
        this.pdfFiles = pdfFiles;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pdf, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        String pdfName = pdfFiles.get(position);
        holder.pdfName.setText(pdfName);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PdfViewerActivity.class);
            intent.putExtra("pdf_name", pdfName);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }

    static class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView pdfName;

        PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.pdfName);
        }
    }
}