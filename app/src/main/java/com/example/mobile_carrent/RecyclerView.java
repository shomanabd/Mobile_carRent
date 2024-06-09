package com.example.mobile_carrent;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RecyclerView extends AppCompatActivity {

    private EditText editTextPickupDate, editTextReturnDate;
    private Calendar calendar;
    private String baseUrl = "http://192.168.56.1/mobile/CarRent/select.php?type=";
    Spinner spnMenu;
    private androidx.recyclerview.widget.RecyclerView recycler;
    ArrayList<CarModel> cars = new ArrayList<>();
    Button search;

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

        populateSpinner();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cars = new ArrayList<>();
                String carbrand = spnMenu.getSelectedItem().toString();
                String startDate = editTextPickupDate.getText().toString();
                String endDate = editTextReturnDate.getText().toString();
                loadItems(carbrand, startDate, endDate);
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RecyclerView.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            editText.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Calendar.Builder()
                                    .setDate(year, month, dayOfMonth)
                                    .build().getTime()));
                        }
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

    private void loadItems(String carbrand, String startDate, String endDate) {
        String requestUrl = baseUrl + carbrand + "&start_date=" + startDate + "&end_date=" + endDate;

        Log.e("URL", requestUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String carName = object.getString("carName");
                                String carDescription = object.getString("carDescription");
                                int seats = object.getInt("Seats");
                                double price = object.getDouble("price");
                                String photo = object.getString("photo");

                                CarModel carModel = new CarModel(carName, carDescription, seats, price, photo);
                                cars.add(carModel);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(RecyclerView.this, cars);
                        recycler.setAdapter(adapter);
                        recycler.setLayoutManager(new LinearLayoutManager(RecyclerView.this));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecyclerView.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(RecyclerView.this).add(stringRequest);
    }
}
