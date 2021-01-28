package com.example.bookzone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class BookImagesRecyclerActivity extends AppCompatActivity {

    private static final int CAMERA_PERM_COND = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private static final String KEY_TITLE = "com.example.bookzone.key.title";
    private static final String KEY_PICTURES_NUMBER = "com.example.bookzone.key.pictures_number";

    private String book_title;
    private int pictures_number;
    private Button add_Image;
    private String currentPhotoPath;
    private Uri contentUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_images_recycler);

        init();
    }

    public void init() {
        book_title = getIntent().getStringExtra(KEY_TITLE);
        pictures_number = getIntent().getIntExtra(KEY_PICTURES_NUMBER, 0);

        TextView titleFragment = findViewById(R.id.textView_titleFragment);
        String title = book_title;
        titleFragment.setText(title);

        TextView subtitleFragment = findViewById(R.id.textView_subtitleFragment);
        String subtitle = "Numar total de poze: " + pictures_number;
        subtitleFragment.setText(subtitle);

        add_Image = findViewById(R.id.button_add_image);
        addImageMethod();
    }

    public void addImageMethod() {
        add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                askCameraPermissions();
            }
        });
    }

//    private void askCameraPermissions() {
//        if(ContextCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_COND);
//        } else {
//            dispatchTakePictureIntent();
//        }
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(this.getBaseContext().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (Exception e) {
//                //
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this.getBaseContext(),
//                        "com.example.bookzone.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//
//                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
//            }
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == BooksRecyclerActivity.RESULT_OK) {
//            File f = new File(currentPhotoPath);
////            bookLoadImage.setImageURI(Uri.fromFile(f));
//
//            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            contentUri = Uri.fromFile(f);
////            mediaScanIntent.setData(contentUri);
////            this.getBaseContext().sendBroadcast(mediaScanIntent);
//
//        }
//    }
//
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = this.getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    private void saveToDB() {
//
//    }

}
