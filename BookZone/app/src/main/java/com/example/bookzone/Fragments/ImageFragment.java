package com.example.bookzone.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookzone.Activities.BookImagesRecyclerActivity;
import com.example.bookzone.R;

public class ImageFragment extends Fragment {

    private ImageView closeFragment;
    private ImageView viewImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_image, parent,false);

        String path = getArguments().getString("IMAGE_PATH");
        Uri pathUri = Uri.parse(path);

        init(view, pathUri);

        return view;
    }

    public void init(View view, Uri pathUri) {
        closeFragment = view.findViewById(R.id.imageView_close_image);
        viewImage = view.findViewById(R.id.imageView__view_image);

        viewImage.setImageURI(pathUri);

        closeFragmentMethod();
    }

    private void closeFragmentMethod() {
        Fragment frag = this;
        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().detach(frag).commit();
            }
        });
    }
}
