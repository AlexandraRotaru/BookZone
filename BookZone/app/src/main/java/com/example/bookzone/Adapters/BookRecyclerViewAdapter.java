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


import java.util.List;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookRecyclerViewHolder> {
    private List<BookEntity> books;
    private LayoutInflater layoutInflater;

    public BookRecyclerViewAdapter(Context context, List<BookEntity> books) {
        layoutInflater = LayoutInflater.from(context);
        this.books = books;
    }

    @NonNull
    @Override
    public BookRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_item, parent, false);
        return new BookRecyclerViewHolder(view);
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

    public class BookRecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView bookImage;
        private TextView bookTitle;

        public BookRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title);
        }

        public void updateData(Uri image, String title) {
            bookImage.setImageURI(image);
            bookTitle.setText(title);
        }
    }
}
