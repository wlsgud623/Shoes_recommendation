package com.example.shoesfinder;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity  {

        Button join;
        Button login;
        SignInButton glogin;
        private EditText email_login;
        private EditText pwd_login;
        private FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            login = (Button) findViewById(R.id.log_in_button);
            join = (Button) findViewById(R.id.sign_button);

            email_login = (EditText) findViewById(R.id.username);
            pwd_login = (EditText) findViewById(R.id.password);

            firebaseAuth = FirebaseAuth.getInstance();

            login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            String email = email_login.getText().toString().trim();
                            String pwd = pwd_login.getText().toString().trim();

                        if(email_login.getText().toString().trim().length() == 0 || email_login.getText().toString().trim().length() == 0){
                            Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력해 주십시오.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                    Toast.makeText(LoginActivity.this, "아이디 또는 패스워드가 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                                                    return;
                                            }
                                    }
                            });
                    }
            });

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });



        }

}
