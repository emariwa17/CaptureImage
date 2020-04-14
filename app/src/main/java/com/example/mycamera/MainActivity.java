package com.example.mycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ImageView;
    private Button viewImageBtn;
    private static final int requestImageCapture = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView = findViewById(R.id.viewImage);
        viewImageBtn = findViewById(R.id.viewImageBtn);
    }

    public void TakePicture(View view) {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, requestImageCapture);
        }

    }

    @Override
    protected void onActivityResult (int required, int result, Intent data) {
        super.onActivityResult(required, result, data);

        if (result == requestImageCapture && result == RESULT_OK ) {

//            Bundle bundle = data.getExtras();

            Bitmap ImageBitmap = (Bitmap) data.getExtras().get("ImageData");
            ImageView.setImageBitmap(ImageBitmap);
        }
    }
}
