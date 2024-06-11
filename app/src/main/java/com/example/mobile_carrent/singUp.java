package com.example.mobile_carrent;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class singUp extends AppCompatActivity {
    private TextView txtFullName, txtUserName, txtEmail, txtPhone, txtPass;
    private Button btnBack, btnEnter;
    String url = "http://192.168.88.3/mobile/CarRent/singUp.php";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        txtFullName = findViewById(R.id.fullname);
        txtUserName = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtPhone = findViewById(R.id.phone);
        txtPass = findViewById(R.id.pass);
        btnEnter = findViewById(R.id.enter);
        btnBack = findViewById(R.id.back_toLogin);

        queue = Volley.newRequestQueue(this);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = txtFullName.getText().toString();
                String userName = txtUserName.getText().toString();
                String email = txtEmail.getText().toString();
                String phoneNumber = txtPhone.getText().toString();
                String password = txtPass.getText().toString();

                if (validateInputs(fullName, userName, email, phoneNumber, password)) {
                    if (isNetworkAvailable()) {
                        sendSignUpRequest(fullName, userName, email, phoneNumber, password);
                    } else {
                        Toast.makeText(singUp.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(singUp.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(singUp.this, loggin.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(String fullName, String userName, String email, String phone, String password) {
        return !fullName.isEmpty() && !userName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void sendSignUpRequest(String fullName, String userName, String email, String phone, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fullName", fullName);
            jsonObject.put("userName", userName);
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(singUp.this, "Error creating JSON object.", Toast.LENGTH_LONG).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(singUp.this, "Created successfully", Toast.LENGTH_LONG).show();
                    Toast.makeText(singUp.this, response.toString(), Toast.LENGTH_LONG).show();

                Intent intent=new Intent(singUp.this,loggin.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(singUp.this, "error", Toast.LENGTH_LONG).show();

            }
        });

        queue.add(request);
    }
}
