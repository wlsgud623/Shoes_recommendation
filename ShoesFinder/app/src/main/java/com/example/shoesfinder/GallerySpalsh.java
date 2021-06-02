package com.example.shoesfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GallerySpalsh extends AppCompatActivity {
    ImageView imageView;
    private final int Gallery_Code = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_spalsh);
        imageView = (ImageView) findViewById(R.id.gallery_image);
        imageView.setImageResource(R.drawable.shoes2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data.getData() != null )  {
            Uri photo = data.getData();
            try {
                Bitmap bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.JPEG,50,stream);
                byte[] Bytearray = stream.toByteArray();
                Intent intent = new Intent(this, PhotoActivity.class);
                intent.putExtra("PhotoforSearch",Bytearray);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this,"Photo Not Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}