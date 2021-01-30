package com.example.bookzone.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookzone.Adapters.ImageRecyclerViewAdapter;
import com.example.bookzone.BookZoneDatabase;
import com.example.bookzone.Dao.ImageDao;
import com.example.bookzone.Entities.ImageEntity;
import com.example.bookzone.Fragments.ImageFragment;
import com.example.bookzone.R;
import com.example.bookzone.Utils.ItemListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookImagesRecyclerActivity extends AppCompatActivity implements ItemListener {

    private static final int CAMERA_PERM_COND = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private static final String KEY_TITLE = "com.example.bookzone.key.title";

    private String book_title;
    private Button add_Image;
    private String currentPhotoPath;
    private Uri contentUri;
    private BookZoneDatabase db;

    ImageRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_images_recycler);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        book_title = getIntent().getStringExtra(KEY_TITLE);

        db = BookZoneDatabase.getAppDatabase(getApplicationContext());

        recyclerViewSets();
        init();
    }

    public void init() {

        TextView titleFragment = findViewById(R.id.textView_titleFragment);
        String title = book_title;
        titleFragment.setText(title);

        TextView subtitleFragment = findViewById(R.id.textView_subtitleFragment);
        String subtitle = "Numar total de poze: " + getNumberOfPic();
        subtitleFragment.setText(subtitle);

        add_Image = findViewById(R.id.button_add_image);
        addImageMethod();
    }


    public void recyclerViewSets() {
        getImagesFromDB();

        RecyclerView imageRecyclerView = findViewById(R.id.recyclerView_book_images);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new ImageRecyclerViewAdapter(this);

        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.setAdapter(adapter);
    }

    public void getImagesFromDB() {
        db.imageDao().getAllImagesForABook(book_title).observe(this, new Observer<List<ImageEntity>>() {
            @Override
            public void onChanged(List<ImageEntity> imageEntities) {
                adapter.setImages(imageEntities);
            }
        });
    }

    public int getNumberOfPic() {
        return db.imageDao().getNumberOfPic(book_title);
    }


    public void addImageMethod() {
        add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_COND);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getBaseContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                //
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this.getBaseContext(),
                        "com.example.bookzone.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == BooksRecyclerActivity.RESULT_OK) {

            File f = new File(currentPhotoPath);
            contentUri = Uri.fromFile(f);

            saveToDB();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveToDB() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    ImageDao imageDao = db.imageDao();
                    imageDao.insertImage(new ImageEntity(contentUri, book_title));

                } catch (Exception e) {
                    throw e;
                }
            }
        };

        thread.start();
    }

    public Uri getImageForAPosition(int position) {
        ImageEntity image = db.imageDao().getImage(position + 1);

        return image.getUriPath();
    }

    @Override
    public void onItemListener(int position) {
        Bundle bundle = new Bundle();

        bundle.putString("IMAGE_PATH", String.valueOf(getImageForAPosition(position)));

        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_fragment_view_image, imageFragment)
                .commit();
    }
}
