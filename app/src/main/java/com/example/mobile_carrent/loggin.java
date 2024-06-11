package com.example.mobile_carrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class loggin extends AppCompatActivity {
    Button btn_newAccount, btn_forget, btn_login;
    EditText txtUserName, txtPassword;
    String url = "http://192.168.88.3/mobile/CarRent/log_in.php"; // Ensure the URL is correct
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loggin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUserName = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        btn_newAccount = findViewById(R.id.btn_newaccount);
        btn_forget = findViewById(R.id.btn_forget);
        btn_login = findViewById(R.id.btn_login);

        queue = Volley.newRequestQueue(this);

        btn_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loggin.this, singUp.class);
                startActivity(intent);
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loggin.this, ForgetPass.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                if (!userName.isEmpty() && !password.isEmpty()) {
                    sendLoginRequest(userName,password);
                } else {
                    Toast.makeText(loggin.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendLoginRequest(String userName, String password) {
        // Create a JSON object to send in the request body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(loggin.this, "Error creating JSON object.", Toast.LENGTH_LONG).show();
            return;
        }

        // Create a StringRequest to send the JSON data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String arg[]= response.split(":");

                            if ('1'==(arg[1].charAt(0))) {
                                Intent intent = new Intent(loggin.this, RecyclerView.class);
                                intent.putExtra("userName", txtUserName.getText().toString());
                                startActivity(intent);
                            }else
                            {
                                Toast.makeText(loggin.this, "invalid username or password", Toast.LENGTH_LONG).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loggin.this, "Network error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            // Define the request body and headers
            @Override
            public byte[] getBody() {
                try {
                    return jsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        // Add the request to the request queue
        queue.add(stringRequest);
    }


}
