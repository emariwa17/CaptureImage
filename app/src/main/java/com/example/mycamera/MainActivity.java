package com.example.mycamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button viewImageBtn;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.viewImage);
        viewImageBtn = findViewById(R.id.viewImageBtn);
    }

    public void TakePicture(View view) {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePicture.resolveActivity(getPackageManager()) != null) {

            File imageFile = null;
            try {
                imageFile = saveImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

                if (imageFile != null) {
                    Uri imageUri = FileProvider.getUriForFile(this,"com.example.android.fileProvider", imageFile);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Bitmap imageBitmap = BitmapFactory.decodeFile(currentImagePath);
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private File saveImage () throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("YYYYMMDD_HHMMSS").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";

        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName, ".jpg",storageDirectory);
        currentImagePath = imageFile.getAbsolutePath();

        return imageFile;
    }
}
