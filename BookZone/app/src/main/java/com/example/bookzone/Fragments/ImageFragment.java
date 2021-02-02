package com.example.bookzone.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookzone.BookZoneDatabase;
import com.example.bookzone.R;


public class ImageFragment extends Fragment {

    private ImageView closeFragment;
    private ImageView viewImage;
    private ImageView deleteImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_image, parent,false);

        String path = getArguments().getString("IMAGE_PATH");
        Uri pathUri = Uri.parse(path);

        init(view, pathUri, path);

        return view;
    }

    public void init(View view, Uri pathUri, String path) {
        closeFragment = view.findViewById(R.id.imageView_close_image);
        viewImage = view.findViewById(R.id.imageView__view_image);
        deleteImage = view.findViewById(R.id.imageView_delete_image);

        viewImage.setImageURI(pathUri);

        closeFragmentMethod();
        deleteImage(path);
    }

    private void closeFragmentMethod() {
        Fragment frag = this;

        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(frag).commit();
            }
        });
    }

    private void deleteImage(String pathImage) {
        Fragment frag = this;

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getContext());
                        db.imageDao().deletePicture(pathImage);

                        getFragmentManager().beginTransaction().remove(frag).commit();
                    }
                }.start();
            }
        });
    }
}
