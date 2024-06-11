package com.example.mobile_carrent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_carrent.ConfirmReservation;
import com.example.mobile_carrent.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<CarModel> carModels;
    private final String userName;
    private final String startDate;
    private final String endDate;

    public RecyclerViewAdapter(Context context, ArrayList<CarModel> carModelArrayList, String userName, String startDate, String endDate) {
        this.context = context;
        this.carModels = carModelArrayList;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarModel carModel = carModels.get(position);
        holder.name.setText(carModel.getCarName());
        holder.description.setText(carModel.getCarDescription());
        holder.price.setText(String.valueOf(carModel.getPrice()));
        holder.seats.setText(String.valueOf(carModel.getSeats()));

        Glide.with(context).load(carModel.getPhoto()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ConfirmReservation.class);
            intent.putExtra("Car_ID", carModel.getCar_ID());
            intent.putExtra("userName", userName);
            intent.putExtra("startDate", startDate);
            intent.putExtra("endDate", endDate);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, seats, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.carImageView);
            name = itemView.findViewById(R.id.carNameTextView);
            description = itemView.findViewById(R.id.carDetailsTextView);
            seats = itemView.findViewById(R.id.seatsTextView);
            price = itemView.findViewById(R.id.carPriceTextView);
        }
    }
}
