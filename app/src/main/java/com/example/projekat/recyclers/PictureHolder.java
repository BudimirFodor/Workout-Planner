package com.example.projekat.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.R;

public class PictureHolder extends RecyclerView.ViewHolder {

    TextView pictureDate;
    TextView pictureHour;
    ImageView pictureImage;

    public PictureHolder(@NonNull View itemView) {
        super(itemView);
        pictureDate = itemView.findViewById(R.id.pictureDate);
        pictureHour = itemView.findViewById(R.id.pictureHour);
        pictureImage = itemView.findViewById(R.id.pictureImage);
    }

    static PictureHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture_card, parent, false);
        return new PictureHolder(view);
    }
}
