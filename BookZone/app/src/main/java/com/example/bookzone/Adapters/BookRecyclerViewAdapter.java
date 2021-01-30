package com.example.bookzone.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookzone.Entities.BookEntity;
import com.example.bookzone.R;
import com.example.bookzone.Utils.ItemListener;


import java.util.ArrayList;
import java.util.List;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookRecyclerViewHolder> {
    private List<BookEntity> books;
    private ItemListener listener;

    public BookRecyclerViewAdapter(ItemListener listener) {
        this.books = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookRecyclerViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRecyclerViewHolder holder, int position) {
        BookEntity book = books.get(position);
        holder.updateData(book.getUriPath(), book.getBookName());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class BookRecyclerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private ImageView bookImage;
        private TextView bookTitle;
        private ItemListener listener;

        public BookRecyclerViewHolder(@NonNull View itemView, ItemListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title);
        }

        public void updateData(Uri image, String title) {
            bookImage.setImageURI(image);
            bookTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            listener.onItemListener(getAdapterPosition());
        }
    }
}
