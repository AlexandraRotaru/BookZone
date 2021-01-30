package com.example.bookzone.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookzone.R;

import com.example.bookzone.Entities.ImageEntity;
import com.example.bookzone.Utils.ItemListener;

import java.util.ArrayList;
import java.util.List;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageRecyclerViewHolder> {

    private List<ImageEntity> allImages;
    private ItemListener listener;

    public ImageRecyclerViewAdapter(ItemListener listener) {
        allImages = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageRecyclerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageRecyclerViewHolder holder, int position) {
        ImageEntity imageEntity = allImages.get(position);
        holder.update(imageEntity.getUriPath());
    }

    @Override
    public int getItemCount() {
        return allImages.size();
    }

    public void setImages(List<ImageEntity> images) {
        this.allImages = images;
        notifyDataSetChanged();
    }

    public class ImageRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private ItemListener listener;

        public ImageRecyclerViewHolder(@NonNull View itemView, ItemListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.image);
        }

        public void update(Uri image) {
            this.image.setImageURI(image);
        }

        @Override
        public void onClick(View v) {
            listener.onItemListener(getAdapterPosition());
        }
    }

}
