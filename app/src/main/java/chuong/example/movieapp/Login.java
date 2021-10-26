package chuong.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import chuong.example.movieapp.Activity.MainActivity;

public class Login extends AppCompatActivity {
    EditText mFullname, mEmail,mPassword,mPhone;
    Button mLoginBtn;
    TextView mCreatBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        mPassword = findViewById(R.id.password);
        mLoginBtn= findViewById(R.id.Loginbtn );
        mCreatBtn = findViewById(R.id.creatText);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();

                if(TextUtils.isEmpty((email))){
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length()<6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth = FirebaseAuth.getInstance();

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged is successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Error!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mCreatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }


}