package com.example.anowarahelpline.blood;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anowarahelpline.R;

import java.util.ArrayList;

public class BloodHistoryFragment extends Fragment {
    BloodHistorySqlite bloodHistorySqlite;
    RecyclerView recyclerView;

    ArrayList<BloodGroupModel> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blood_history, container, false);


        recyclerView = view.findViewById(R.id.blood_history_recylerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        bloodHistorySqlite = new BloodHistorySqlite(getContext());


        Cursor cursor = bloodHistorySqlite.getAllData();



        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String bloodGroup = cursor.getString(1);
            String mobileNumber = cursor.getString(2);
            String email = cursor.getString(3);
            String imgUri = cursor.getString(4);
            String name    = cursor.getString(5);

            Log.d("sql",name);
            Log.d("sql",mobileNumber);
            Log.d("sql",email);
            Log.d("sql",bloodGroup);
            Log.d("sql",imgUri);


            BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);
            arrayList.add(bloodGroupModel);

            BloodDonerAdapter bloodDonerAdapter = new BloodDonerAdapter(getContext(),arrayList);
            recyclerView.setAdapter(bloodDonerAdapter);

            Log.d("osman",name);
        }
        return view;
    }
}