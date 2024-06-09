package com.example.mobile_carrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetEmail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btn, btnback;
    private EditText email;
    private String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_email);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailtext);
        btn = findViewById(R.id.buttonrest);
        btnback = findViewById(R.id.back);
        // progressBar = findViewById(R.id.progressBar);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetEmail.this, ForgetPass.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        strEmail = email.getText().toString().trim();
        if (strEmail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Please enter a valid email address");
            email.requestFocus();
        } else {
            forget();
        }
    }

    private void forget() {
        mAuth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetEmail.this, "Check your email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetEmail.this, loggin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ResetEmail.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

