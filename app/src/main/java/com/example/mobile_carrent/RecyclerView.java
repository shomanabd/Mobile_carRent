package com.example.mobile_carrent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_carrent.CarModel;
import com.example.mobile_carrent.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecyclerView extends AppCompatActivity {

    private EditText editTextPickupDate, editTextReturnDate;
    private Calendar calendar;
    private String baseUrl = "http://192.168.88.3/mobile/CarRent/select.php?type=";
    Spinner spnMenu;
    private androidx.recyclerview.widget.RecyclerView recycler;
    ArrayList<CarModel> cars = new ArrayList<>();
    Button search;
    private boolean isPickupDateValid = false;
    private boolean isReturnDateValid = false;

    int carID;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_cars);

        recycler = findViewById(R.id.cars_recycle_view);
        spnMenu = findViewById(R.id.spnMenu);
        search = findViewById(R.id.button_id);
        editTextPickupDate = findViewById(R.id.editText_pickup_date);
        editTextReturnDate = findViewById(R.id.editText_return_date);

        calendar = Calendar.getInstance();

        // Receive the username from the intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        // Set up date pickers
        editTextPickupDate.setOnClickListener(v -> showDatePickerDialog(editTextPickupDate));
        editTextReturnDate.setOnClickListener(v -> showDatePickerDialog(editTextReturnDate));

        // Populate car brand spinner
        populateSpinner();

        // Initialize search button state and set on click listener
        search.setEnabled(false); // Make sure the button is initially disabled
        search.setOnClickListener(v -> {
            cars = new ArrayList<>();
            String carBrand = spnMenu.getSelectedItem().toString();
            String startDate = editTextPickupDate.getText().toString();
            String endDate = editTextReturnDate.getText().toString();
            loadItems(carBrand, startDate, endDate);
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RecyclerView.this,
                (view, year, month, dayOfMonth) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        editText.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Calendar.Builder()
                                .setDate(year, month, dayOfMonth)
                                .build().getTime()));
                        if (editText == editTextPickupDate) {
                            isPickupDateValid = !editTextPickupDate.getText().toString().isEmpty();
                        } else if (editText == editTextReturnDate) {
                            isReturnDateValid = !editTextReturnDate.getText().toString().isEmpty();
                        }
                        updateSearchButtonState();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.carBrands));
        spnMenu.setAdapter(adapter);
    }

    private void updateSearchButtonState() {
        search.setEnabled(isPickupDateValid && isReturnDateValid);
    }

    private void loadItems(String carBrand, String startDate, String endDate) {
        String requestUrl = baseUrl + carBrand + "&start_date=" + startDate + "&end_date=" + endDate;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                response -> {
                    Log.e("Response", response);
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            carID = object.getInt("Car_ID");
                            String carName = object.getString("carName");
                            String carDescription = object.getString("carDescription");
                            int seats = object.getInt("Seats");
                            double price = object.getDouble("price");
                            String photo = object.getString("photo");

                            CarModel carModel = new CarModel(carID, carName, carDescription, seats, price, photo, userName);
                            cars.add(carModel);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(RecyclerView.this, cars, userName, startDate, endDate);
                    recycler.setAdapter(adapter);
                    recycler.setLayoutManager(new LinearLayoutManager(RecyclerView.this));
                },
                error -> Toast.makeText(RecyclerView.this, error.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(RecyclerView.this).add(stringRequest);
    }
}
