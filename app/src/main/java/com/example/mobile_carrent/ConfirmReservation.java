package com.example.mobile_carrent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class ConfirmReservation extends AppCompatActivity {

    private EditText editTextCardNumber, editTextCVV, editTextCardholderName;
    private Calendar calendar;
    private String userName, startDate, endDate;
    private int carID;
    private String baseUrl = "http://192.168.88.3/mobile/CarRent/insert.php?Car_ID=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);

        // Initialize views
        editTextCardNumber = findViewById(R.id.editText_card_number);
        editTextCVV = findViewById(R.id.editText_cvv);
        editTextCardholderName = findViewById(R.id.editText_cardholder_name);
        Button buttonSubmit = findViewById(R.id.button_submit); // Assuming this button is meant to submit the payment

        // Retrieve the data passed from the previous activity
        Intent intent = getIntent();
        carID = intent.getIntExtra("Car_ID", -1);
        userName = intent.getStringExtra("userName");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");




        buttonSubmit.setOnClickListener(v -> {
            // Validate input fields
            if (validateInputs()) {


                String requestUrl = baseUrl + carID + "&userName=" + userName + "&startDate=" + startDate+"&endDate="+endDate ;

                Log.e("url",requestUrl);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                        response -> {

                            try {
                                Log.e("responce",response.toString());
                                if("New record created successfully".equals(response.toString())){


                                    Toast.makeText(this, "Your payment has been submitted successfully and reservation confirmed", Toast.LENGTH_SHORT).show();
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        },


                        error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show());
                Volley.newRequestQueue(ConfirmReservation.this).add(stringRequest);
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ConfirmReservation.this,
                (view, year, month, dayOfMonth) -> editText.setText(year + "-" + (month + 1) + "-" + dayOfMonth),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private boolean validateInputs() {
        // Validate each input field
        if (editTextCardNumber.getText().toString().isEmpty()) {
            editTextCardNumber.setError("Card Number is required");
            return false;
        }

        if (editTextCVV.getText().toString().isEmpty()) {
            editTextCVV.setError("CVV is required");
            return false;
        }
        if (editTextCardholderName.getText().toString().isEmpty()) {
            editTextCardholderName.setError("Cardholder Name is required");
            return false;
        }
        return true;
    }
}
