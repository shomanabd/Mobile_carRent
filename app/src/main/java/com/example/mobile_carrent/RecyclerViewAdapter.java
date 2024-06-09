package com.example.mobile_carrent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<CarModel> carModels;
    public RecyclerViewAdapter(Context context, ArrayList<CarModel> carModelArrayList){

        this.context=context;
        this.carModels=carModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(carModels.get(position).getCarName());
        holder.description.setText(carModels.get(position).getCarDescription());
        holder.price.setText(carModels.get(position).getPrice()+"");
        holder.seats.setText(carModels.get(position).getSeats()+"");

        Glide.with(context).load(carModels.get(position).getPhoto()).into(holder.imageView);

      holder.imageView.setOnClickListener( new View.OnClickListener(){
          @Override
          public void onClick(View v) {

              Toast.makeText(context, "Item clicked: " , Toast.LENGTH_SHORT).show();
          }
      });

    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,description,seats,price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =itemView.findViewById(R.id.carImageView);
            name = itemView.findViewById(R.id.carNameTextView);
            description =itemView.findViewById(R.id.carDetailsTextView);
            seats=itemView.findViewById(R.id.seatsTextView);
            price=itemView.findViewById(R.id.carPriceTextView);
        }


    }
}
