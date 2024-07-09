package com.example.anowarahelpline.blood;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.anowarahelpline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class BloodDonerAdapter extends RecyclerView.Adapter<BloodDonerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<BloodGroupModel> bLoodDonerArrayList;


    public BloodDonerAdapter(Context context, ArrayList<BloodGroupModel> bLoodDonerArrayList) {
        this.context = context;
        this.bLoodDonerArrayList = bLoodDonerArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =layoutInflater.inflate(R.layout.blood_donar_show_item,parent,false);
        return new MyViewHolder(view);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         BloodGroupModel bLoodDonerModel = bLoodDonerArrayList.get(position);

         String name = bLoodDonerModel.getName();
         String bloodGroup = bLoodDonerModel.getBloodGroup();
         String mobileNumber = bLoodDonerModel.getMobileNumber();
         String imgUri = bLoodDonerModel.getDownloadImgUri();
         String email = bLoodDonerModel.getEmail();


        holder.name.setText(name);
        holder.bloodGroup.setText(bloodGroup);

        //show red and green color accourding to days

        Picasso.get()
                .load(imgUri)
                .fit()
                .centerCrop()
                .into(holder.imageView);


        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                sendDataToSqlite(name,mobileNumber,email,imgUri,bloodGroup);
                CallFunction(mobileNumber);
            }
        });
        holder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToSqlite(name,mobileNumber,email,imgUri,bloodGroup);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode(mobileNumber))); // Uri.encode ensures the phone number is properly formatted
                // Start the activity for the intent
                context.startActivity(intent);

            }

        });
    }

    //send data to sqlite
    private void sendDataToSqlite(String name, String mobileNumber, String email, String imgUri, String bloodGroup) {

        BloodHistorySqlite bloodHistorySqlite = new BloodHistorySqlite(context);

        bloodHistorySqlite.insertData(name,mobileNumber,email,imgUri,bloodGroup);



    }



    private void CallFunction(String mobileNumber) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+mobileNumber));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return bLoodDonerArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,bloodGroup;
        CircleImageView imageView;
        ImageView callButton,messageBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.bloodDonerShowItemName);
            bloodGroup = itemView.findViewById(R.id.bloodDonerShowItemBLoodGroup);
            imageView = itemView.findViewById(R.id.bloodDonerShowItemImg);
            callButton = itemView.findViewById(R.id.bloodDonerShowItemPhoneLogo);
            messageBtn = itemView.findViewById(R.id.bloodDonerShowItemMassage);
        }
    }
}
