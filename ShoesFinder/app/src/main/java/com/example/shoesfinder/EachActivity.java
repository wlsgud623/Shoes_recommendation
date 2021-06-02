package com.example.shoesfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class EachActivity extends AppCompatActivity {
    ImageView imageView;
    TextView nameview, priceview, brandview;
    TextView rateNumber, participateNumber;
    ListView CommentList;
    DocumentReference documentReference, userlog, mylog;
    View RateView, RatingView, InputCommentView;
    EditText commentwrite;
    Spinner ratespinner;
    RatingBar ratingBar;
    Button ratebutton, commentbutton;
    DocumentSnapshot documentSnapshot;
    FirebaseFirestore db;
    String document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each);

        Intent intent = getIntent();

        document = intent.getStringExtra("document");

        db = FirebaseFirestore.getInstance();

        RateView = (View) findViewById(R.id.rated);
        RatingView = (View) findViewById(R.id.rating);
        InputCommentView = (View) findViewById(R.id.commentwindow);

        imageView = (ImageView) findViewById(R.id.each_picture);
        nameview = (TextView) findViewById(R.id.each_name);
        priceview = (TextView) findViewById(R.id.each_price);
        brandview = (TextView) findViewById(R.id.each_brand);

        ratingBar = (RatingBar) RateView.findViewById(R.id.ratebar);
        rateNumber = (TextView) RateView.findViewById(R.id.ratenumber);
        participateNumber = (TextView) RateView.findViewById(R.id.partnumber);

        ratespinner = (Spinner) RatingView.findViewById(R.id.ratespinner);
        ratebutton = (Button) RatingView.findViewById(R.id.ratingButton);

        commentbutton = (Button) InputCommentView.findViewById(R.id.commentButton);
        commentwrite = (EditText) InputCommentView.findViewById(R.id.writtentext);


        CommentList = (ListView)findViewById(R.id.comment_list);
        CommentAdapter commentAdapter = new CommentAdapter(this);
        CommentList.setAdapter(commentAdapter);

        userlog = db.collection("user").document("All");
        userlog.update("visited", FieldValue.arrayUnion(document)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        mylog = db.collection("user").document(MainActivity.id);
        mylog.update("visited", FieldValue.arrayUnion(document)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        db.collection("shose").document(document).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    nameview.setText(documentSnapshot.get("name").toString());
                    priceview.setText("가격 : " + documentSnapshot.get("price").toString() + " 만원");
                    brandview.setText("제조사 : " + documentSnapshot.get("brand").toString());
                    float rate = (float)Integer.parseInt(documentSnapshot.get("rate").toString()) / Integer.parseInt(documentSnapshot.get("participate").toString());
                    ratingBar.setRating(rate);
                    String rates = String.format("%.2f",rate);
                    rateNumber.setText(rates);
                    participateNumber.setText("(참여 인원 : " + documentSnapshot.get("participate").toString() + "명)");

                    List<String> comments = (List<String>) documentSnapshot.get("comment");
                    commentAdapter.additem(comments);
                    commentAdapter.notifyDataSetChanged();
                    Log.d("co",comments.toString());

                    Glide.with(EachActivity.this).load(documentSnapshot.get("image").toString()).override(300,300).into(imageView);
                }
                else{
                    Toast.makeText(EachActivity.this,"존재하지 않는 문서입니다" , Toast.LENGTH_SHORT).show();
                }
            }
        });


        ratebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userrate = 5 - ratespinner.getSelectedItemPosition();
                int adduser = 1;
                documentReference = db.collection("shose").document(document);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            documentSnapshot = task.getResult();
                            if(documentSnapshot.get("ID").toString().contains(MainActivity.id)){
                                //Toast.makeText(EachActivity.this,"이미 평점을 등록하였습니다" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                documentReference.update("ID", FieldValue.arrayUnion(MainActivity.id));
                documentReference.update("participate", FieldValue.increment(adduser)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                documentReference.update("rate", FieldValue.increment(userrate)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EachActivity.this,"평점이 등록되었습니다" , Toast.LENGTH_SHORT).show();
                        Intent intent1 = getIntent();
                        finish();
                        startActivity(intent1);
                    }
                });


            }
        });

        commentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentReference = db.collection("shose").document(document);
                String s = commentwrite.getText().toString();
                documentReference.update("comment", FieldValue.arrayUnion(s));
                Toast.makeText(EachActivity.this,"등록되었습니다" , Toast.LENGTH_SHORT).show();
                commentAdapter.notifyDataSetChanged();
                Intent intent1 = getIntent();
                finish();
                startActivity(intent1);
            }
        });




    }
}