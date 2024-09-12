package com.example.anowarahelpline.hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.blood.BloodGroupModel;

import java.util.ArrayList;
import java.util.HashMap;

public class HospitalRecylerView extends RecyclerView.Adapter<HospitalRecylerView.MyViewHospital> {


    private Context context;
    private ArrayList<HashMap<String,String>> hospitalModels;

    public HospitalRecylerView(Context context, ArrayList<HashMap<String, String>> hospitalModels) {
        this.context = context;
        this.hospitalModels = hospitalModels;
    }

    @NonNull
    @Override
    public MyViewHospital onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hospital, parent, false);
        return new MyViewHospital(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHospital holder, int position) {
        HashMap<String,String> hospitalModel = hospitalModels.get(position);
        holder.nametxt.setText(hospitalModel.get("name"));
        holder.addresstxt.setText(hospitalModel.get("address"));
        holder.phonetxt.setText(hospitalModel.get("phone"));

        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return hospitalModels.size();
    }

    public class MyViewHospital extends RecyclerView.ViewHolder{
        TextView nametxt, addresstxt, phonetxt;
        public MyViewHospital(@NonNull View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.hospitalName);
            addresstxt = itemView.findViewById(R.id.hospitalAdress);
            phonetxt = itemView.findViewById(R.id.hospitalPhone);

        }
    }

    public void setAnimation(View itemView, int position) {
        Animation slideIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(slideIn);
    }
    public void setSearchList(ArrayList<HashMap<String,String>> dataSearchList){
        this.hospitalModels = dataSearchList;
        notifyDataSetChanged();
    }
}
