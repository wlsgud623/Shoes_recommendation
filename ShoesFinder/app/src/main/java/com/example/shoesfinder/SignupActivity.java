package com.example.shoesfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText email_join, pass_join, name_join, age_join, gender_join;
    Button signButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email_join = (EditText) findViewById(R.id.input_email);
        pass_join = (EditText) findViewById(R.id.input_password);
        name_join = (EditText) findViewById(R.id.input_name);
        age_join = (EditText) findViewById(R.id.input_age);
        gender_join = (EditText) findViewById(R.id.input_gender);

        signButton = (Button) findViewById(R.id.sign_confirm_button);
        auth = FirebaseAuth.getInstance();

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_join.getText().toString().trim().length() == 0 || pass_join.getText().toString().trim().length() == 0 ||
                        name_join.getText().toString().trim().length() == 0 || age_join.getText().toString().trim().length() == 0 || gender_join.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(SignupActivity.this, "모든 정보를 입력하십시오", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = email_join.getText().toString().trim();
                final String pwd = pass_join.getText().toString().trim();
                final String age = age_join.getText().toString().trim();
                final String name = name_join.getText().toString().trim();
                final String gender = gender_join.getText().toString().trim();

                auth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

            }
        });
    }
}