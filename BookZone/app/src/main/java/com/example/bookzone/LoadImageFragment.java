package com.example.bookzone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.bookzone.Adapters.BookRecyclerViewAdapter;
import com.example.bookzone.Dao.BookDao;
import com.example.bookzone.Entities.BookEntity;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class LoadImageFragment extends Fragment {

    private static final int CAMERA_PERM_COND = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private ImageView closeFragment;
    private ImageView bookLoadImage;
    private EditText bookTitle;
    private String currentPhotoPath;
    private Uri contentUri;
    private Button saveImageToDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_image, parent, false);

        init(view);

        return view;
    }

    private void init(View view) {
        closeFragment = view.findViewById(R.id.imageView_close_fragment);
        bookLoadImage = view.findViewById(R.id.imageButton_load_image);
        bookTitle = view.findViewById(R.id.editText_book_title);
        saveImageToDB = view.findViewById(R.id.button_save_image_in_db);

        closeFragmentMethod();
        loadImageMethod();
        saveImageToDB();
    }

    private void closeFragmentMethod() {
        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(LoadImageFragment.this).commit();
                BooksRecyclerActivity.setAdd_book();
            }
        });
    }

    private void saveImageToDB() {
        saveImageToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleForBook = bookTitle.getText().toString();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            BookZoneDatabase db = BookZoneDatabase.getAppDatabase(getActivity().getApplicationContext());
                            BookDao bookDao = db.bookDao();

                            BookEntity bookEntity = bookDao.getBook(titleForBook);

                            if(bookEntity == null && !titleForBook.isEmpty()) {
                                BookEntity book = new BookEntity(titleForBook, contentUri);
                                bookDao.insertBook(book);
                            }

                        } catch (
                                Exception e) {
                            throw e;
                        }
                    }
                };

                thread.start();
            }
        });
    }

    private void loadImageMethod() {
        bookLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_COND);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_COND) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getContext(), "Camera Permission e necesara pentru folosirea camerei.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                //
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.bookzone.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == BooksRecyclerActivity.RESULT_OK) {
            File f = new File(currentPhotoPath);
            bookLoadImage.setImageURI(Uri.fromFile(f));

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            getContext().sendBroadcast(mediaScanIntent);

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}