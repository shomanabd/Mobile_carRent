package com.example.mobile_carrent;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ConfirmReservation extends AppCompatActivity {

    private EditText editTextPickupDate, editTextReturnDate;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);


        editTextPickupDate = findViewById(R.id.editText_pickup_date);
        editTextReturnDate = findViewById(R.id.editText_return_date);
        EditText editTextPickupLocation = findViewById(R.id.editText_pickup_location);
        EditText editTextFullName = findViewById(R.id.editText_full_name);
        EditText editTextPhone = findViewById(R.id.editText_phone);
        EditText editTextEmail = findViewById(R.id.editText_email);
        CheckBox checkBoxTerms = findViewById(R.id.checkBox_terms);
        Button buttonConfirm = findViewById(R.id.button_confirm);

        calendar = Calendar.getInstance();

        editTextPickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextPickupDate);
            }
        });

        editTextReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextReturnDate);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs(editTextFullName, editTextPhone, editTextEmail, checkBoxTerms)) {

                    Toast.makeText(ConfirmReservation.this, "Your reservation has been confirmed successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ConfirmReservation.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private boolean validateInputs(EditText fullName, EditText phone, EditText email, CheckBox terms) {
        if (fullName.getText().toString().isEmpty()) {
            fullName.setError("insert full name");
            return false;
        }
        if (phone.getText().toString().isEmpty()) {
            phone.setError("insert phone number");
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("insert your email ");
            return false;
        }
        if (!terms.isChecked()) {
            Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}