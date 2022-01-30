package com.example.projekat.recyclers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.projekat.classes.Picture;

public class PictureAdapter extends ListAdapter<Picture, PictureHolder> {

    Context context;

    public PictureAdapter(Context context, @NonNull DiffUtil.ItemCallback<Picture> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PictureHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureHolder holder, int position) {
        holder.pictureDate.setText(String.valueOf(getItem(position).getDate()));
        holder.pictureHour.setText(String.valueOf(getItem(position).getHour()));

        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(getItem(position).getImageUri()));
        holder.pictureImage.setImageBitmap(bitmap);

    }

    public static class PictureDiff extends DiffUtil.ItemCallback<Picture> {

        @Override
        public boolean areItemsTheSame(@NonNull Picture oldItem, @NonNull Picture newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Picture oldItem, @NonNull Picture newItem) {
            return oldItem.getHour().equals(newItem.getHour()) && oldItem.getDate().equals(newItem.getDate());
        }
    }
}
