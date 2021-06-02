package com.example.shoesfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ResultActivity extends AppCompatActivity {

    Bitmap br;
    ImageView img, img_res;
    View resulteview, similar_shoes, resultText;
    FirebaseFirestore db;
    DocumentReference doc;
    View ss, ts, fs;
    TextView fb, fp;
    TextView sst, tst, fst;
    TextView second_price, third_price, fourth_price;
    ImageView sp, tp, fop;
    String[] barr, parr;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        byte[] arr = intent.getByteArrayExtra("PhotoforSearch");
        br = BitmapFactory.decodeByteArray(arr,0, arr.length);
        db = FirebaseFirestore.getInstance();


        resulteview = (View) findViewById(R.id.picture_result);
        similar_shoes = (View) findViewById(R.id.similar_shoes);
        resultText = (View) findViewById(R.id.name_result);

        ss = (View)similar_shoes.findViewById(R.id.first_Shoes);
        ts = (View)similar_shoes.findViewById(R.id.second_Shoes);
        fs = (View)similar_shoes.findViewById(R.id.third_Shoes);

        sst = (TextView)ss.findViewById(R.id.Shoes_Name);
        tst = (TextView)ts.findViewById(R.id.Shoes_Name);
        fst = (TextView)fs.findViewById(R.id.Shoes_Name);

        second_price = (TextView)ss.findViewById(R.id.Shows_Price);
        third_price = (TextView)ts.findViewById(R.id.Shows_Price);
        fourth_price = (TextView)fs.findViewById(R.id.Shows_Price);

        sp = (ImageView)ss.findViewById(R.id.Shoes_Photo);
        tp = (ImageView)ts.findViewById(R.id.Shoes_Photo);
        fop = (ImageView)fs.findViewById(R.id.Shoes_Photo);

        img = (ImageView)resulteview.findViewById(R.id.original_small_pic);
        img.setImageBitmap(br);

        img_res = (ImageView) resulteview.findViewById(R.id.result_small_pic);


        //fb = (TextView) resulteview.findViewById(R.id.first_brand);
        //fb.setText(barr[0]);

        barr = intent.getStringArrayExtra("Brandarr");
        parr = intent.getStringArrayExtra("Percent");

        fb = (TextView)resultText.findViewById(R.id.result_shoes_name);
        fp = (TextView)resultText.findViewById(R.id.result_shoes_percent);

        doc = db.collection("shose").document(barr[0]);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    fb.setText(documentSnapshot.get("name").toString());
                    Glide.with(ResultActivity.this).load(documentSnapshot.get("image").toString()).override(160,200).into(img_res);
                }
            }
        });
        fp.setText(parr[0] + "% 일치");
        doc = db.collection("shose").document(barr[1]);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    sst.setText(documentSnapshot.get("name").toString());
                    second_price.setText(documentSnapshot.get("price").toString() + " 만원");
                    Glide.with(ResultActivity.this).load(documentSnapshot.get("image").toString()).override(160,200).into(sp);
                }
            }
        });

        doc = db.collection("shose").document(barr[2]);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    tst.setText(documentSnapshot.get("name").toString());
                    third_price.setText(documentSnapshot.get("price").toString() + " 만원");
                    Glide.with(ResultActivity.this).load(documentSnapshot.get("image").toString()).override(160,200).into(tp);
                }
            }
        });

        doc = db.collection("shose").document(barr[3]);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    fst.setText(documentSnapshot.get("name").toString());
                    fourth_price.setText(documentSnapshot.get("price").toString() + " 만원");
                    Glide.with(ResultActivity.this).load(documentSnapshot.get("image").toString()).override(160,200).into(fop);
                }
            }
        });

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, EachActivity.class);
                intent.putExtra("document", barr[1]);
                startActivity(intent);
            }
        });

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, EachActivity.class);
                intent.putExtra("document", barr[2]);
                startActivity(intent);
            }
        });

        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, EachActivity.class);
                intent.putExtra("document", barr[3]);
                startActivity(intent);
            }
        });
    }
}