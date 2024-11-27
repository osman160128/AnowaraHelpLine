package com.example.anowarahelpline.hospital;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        String phone1 = hospitalModel.get("phone1");
        String phone2 = hospitalModel.get("phone2");
        String phone3 = hospitalModel.get("phone3");
        String phone4 = hospitalModel.get("phone4");

        holder.callHospitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCallDialog(phone1,phone2,phone3,phone4);
            }
        });



        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return hospitalModels.size();
    }

    public class MyViewHospital extends RecyclerView.ViewHolder{
        TextView nametxt, addresstxt,callHospitalBtn;
        public MyViewHospital(@NonNull View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.hospitalName);
            addresstxt = itemView.findViewById(R.id.hospitalAdress);
            callHospitalBtn = itemView.findViewById(R.id.callHospitalPhoneBtn);

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

    private void showCallDialog(String phone1,String phone2,String phone3,String phone4) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView  = layoutInflater.inflate(R.layout.call_layout,null);

        TextView phoneNUmber1 = dialogView.findViewById(R.id.phoneNumverTxt1);
        TextView phoneNUmber2 = dialogView.findViewById(R.id.phoneNumverTxt2);
        TextView phoneNUmber3 = dialogView.findViewById(R.id.phoneNumverTxt3);
        TextView phoneNUmber4 = dialogView.findViewById(R.id.phoneNumverTxt4);


        CardView phoneNumberBtn1= dialogView.findViewById(R.id.phoneCallCardView1);
        CardView phoneNumberBtn2= dialogView.findViewById(R.id.phoneCallCardView2);
        CardView phoneNumberBtn3= dialogView.findViewById(R.id.phoneCallCardView3);
        CardView phoneNumberBtn4= dialogView.findViewById(R.id.phoneCallCardView4);

        LinearLayout phoneLayout1 = dialogView.findViewById(R.id.phoneCallLayout1);
        LinearLayout phoneLayout2 = dialogView.findViewById(R.id.phoneCallLayout2);
        LinearLayout phoneLayout3 = dialogView.findViewById(R.id.phoneCallLayout3);
        LinearLayout phoneLayout4 = dialogView.findViewById(R.id.phoneCallLayout4);

        if(!phone1.isEmpty()){
            phoneNUmber1.setText(phone1);
            phoneNumberBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone1));
                    context.startActivity(intent);
                }
            });
        }else{
            phoneLayout1.setVisibility(View.GONE);
        }

        if(!phone2.isEmpty()){
            phoneNUmber2.setText(phone2);
            phoneNumberBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone2));
                    context.startActivity(intent);
                }
            });
        }else{
            phoneLayout2.setVisibility(View.GONE);
        }

        if(!phone3.isEmpty()){
            phoneNUmber3.setText(phone3);
            phoneNumberBtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone3));
                    context.startActivity(intent);
                }
            });
        }else{
            phoneLayout3.setVisibility(View.GONE);
        }

        if(!phone4.isEmpty()){
            phoneNUmber4.setText(phone4);
            phoneNumberBtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone4));
                    context.startActivity(intent);
                }
            });
        }else{
            phoneLayout4.setVisibility(View.GONE);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Call Number")
                .setView(dialogView) // Set the custom view
                .setPositiveButton("Close", null)
                .show();

    }
}
