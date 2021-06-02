package com.example.shoesfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.os.SystemClock.sleep;

public class CameraSplash extends AppCompatActivity {

    private final int Image_Capture_Code = 1;
    ImageView imageView;
    Bitmap bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_splash);
        imageView = (ImageView) findViewById(R.id.camera_image);
        imageView.setImageResource(R.drawable.shoes1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityCompat.requestPermissions(CameraSplash.this, new String[]{Manifest.permission.CAMERA},1);
                PackageManager packageManager = CameraSplash.this.getPackageManager();

                if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
                    Toast.makeText(CameraSplash.this, "카메라에 연결할수 없습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CameraSplash.this, MainActivity.class);
                    startActivity(intent);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Image_Capture_Code);
            }
        }, 2500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG,50,stream);
            byte[] Bytearray = stream.toByteArray();
            Intent intent = new Intent(this, PhotoActivity.class);
            intent.putExtra("PhotoforSearch",Bytearray);
            startActivity(intent);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this,"Photo not selected", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}