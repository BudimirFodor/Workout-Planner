package com.example.projekat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat.classes.Picture;
import com.example.projekat.recyclers.PictureAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PicturesPage  extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures_page);

        Button picturesButton = findViewById(R.id.slikaj);

        recyclerView = findViewById(R.id.picturesRecyclerView);
        final PictureAdapter pictureAdapter = new PictureAdapter(this, new PictureAdapter.PictureDiff());
        recyclerView.setAdapter(pictureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        picturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        String path = Environment.getExternalStorageDirectory() + "/Android/data/com.example.projekat/files/Pictures/";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

        File[] files = directory.listFiles();
        List<Picture> pictures = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++)
            {
                String fileUrl = files[i].getName();
                String[] parsedFile = fileUrl.replace("Progress_pic_", "").split("_");
                Picture picture = new Picture();
                picture.setDate(parsedFile[0].substring(4, 6) + "-" + parsedFile[0].substring(6) + "-" + parsedFile[0].substring(0, 4));
                picture.setHour(parsedFile[1].substring(0, 2) + ":" + parsedFile[1].substring(2, 4) + ":" + parsedFile[1].substring(4));
                picture.setImageUri(path + fileUrl);

                pictures.add(picture);
            }
        }

        pictureAdapter.submitList(pictures);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error while saving picture" , Toast.LENGTH_SHORT);
                toast.show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.projekat.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Progress_pic_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
