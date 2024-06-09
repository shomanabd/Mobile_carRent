

package com.example.mobile_carrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;

public class ForgetPass extends AppCompatActivity {
    String resetEmail;
    Button btn_back,btn_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);

        btn_back = findViewById(R.id.forget_back);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPass.this,loggin.class); // Ensure SignUp class name is correct
                startActivity(intent);
            }
        });
        btn_reset = findViewById(R.id.resetPassword);


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForgetPass.this,ResetEmail.class); // Ensure SignUp class name is correct
                startActivity(intent);

            }
        });
    }
}