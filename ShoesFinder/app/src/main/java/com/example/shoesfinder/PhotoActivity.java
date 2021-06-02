package com.example.shoesfinder;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PhotoActivity extends AppCompatActivity {
    Bitmap bm;
    ImageView img;
    Button bb, sb;
    private RequestQueue queue;
    Dialog dialog;

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();


        byte[] arr = intent.getByteArrayExtra("PhotoforSearch");
        bm = BitmapFactory.decodeByteArray(arr,0, arr.length);
        img = (ImageView)findViewById(R.id.Photoview);
        img.setImageBitmap(bm);

        bb = (Button) findViewById(R.id.Backbutton);
        sb = (Button) findViewById(R.id.SearchButton);
        queue = Volley.newRequestQueue(this);
        String url = "http://192.168.35.59:5000/check";


        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PhotoActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new LoadingDialog(PhotoActivity.this);
                dialog.show();

                String encodedimage = getStringFromBitmap(bm);
                Map<String, String> params = new HashMap();
                params.put("bitarray", encodedimage);

                JSONObject jso = new JSONObject(params);

                final JsonObjectRequest jsonobject = new JsonObjectRequest(Request.Method.POST, url, jso, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("post", response+"");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        String[] brands = new String[0];
                        String[] percents = new String[0];
                        try {
                            brands = new String[4];
                            percents = new String[4];
                            brands[0] = response.getString("first_brand");
                            brands[1] = response.getString("second_brand");
                            brands[2] = response.getString("third_brand");
                            brands[3] = response.getString("fourth_brand");
                            percents[0] = response.getString("first_percentage");
                            percents[1] = response.getString("second_percentage");
                            percents[2] = response.getString("third_percentage");
                            percents[3] = response.getString("fourth_percentage");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bm.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                        byte[] Bytearray = stream.toByteArray();
                        Intent intent = new Intent(PhotoActivity.this, ResultActivity.class);
                        intent.putExtra("PhotoforSearch", Bytearray);
                        intent.putExtra("Brandarr", brands);
                        intent.putExtra("Percent", percents);
                        dialog.cancel();
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        Toast.makeText(PhotoActivity.this,"서버와의 연결이 원활하지 않습니다." , Toast.LENGTH_SHORT).show();

                    }
                });

                jsonobject.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(jsonobject);

            }
        });
    }
}