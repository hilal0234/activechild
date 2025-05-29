package com.example.activechild;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EtkinlikAdapter extends RecyclerView.Adapter<EtkinlikAdapter.ViewHolder> {

    private Context context;
    private List<Etkinlik> etkinlikList;

    public EtkinlikAdapter(Context context, List<Etkinlik> etkinlikList) {
        this.context = context;
        this.etkinlikList = etkinlikList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.etkinlik_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etkinlik etkinlik = etkinlikList.get(position);

        holder.txtBaslik.setText(etkinlik.getBaslik());
        holder.txtAciklama.setText(etkinlik.getAciklama());

        Glide.with(context)
                .load(etkinlik.getResimUrl())
                .into(holder.imgEtkinlik);

        holder.btnEtkinlik.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(etkinlik.getKaynakUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return etkinlikList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBaslik, txtAciklama;
        ImageView imgEtkinlik;
        Button btnEtkinlik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBaslik = itemView.findViewById(R.id.txtBaslik);
            txtAciklama = itemView.findViewById(R.id.txtAciklama);
            imgEtkinlik = itemView.findViewById(R.id.imgEtkinlik);
            btnEtkinlik = itemView.findViewById(R.id.etkinliklerButton);
        }
    }
}
