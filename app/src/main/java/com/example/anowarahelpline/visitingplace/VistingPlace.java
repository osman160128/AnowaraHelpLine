package com.example.anowarahelpline.visitingplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.ActivityVistingPlaceBinding;

public class VistingPlace extends AppCompatActivity {

    CardView parkiBeach,tarnelRoad,hiltopPark,mennaGarden;

    ActivityVistingPlaceBinding activityVistingPlaceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visting_place);

        activityVistingPlaceBinding = DataBindingUtil.setContentView(this,R.layout.activity_visting_place);
        activityVistingPlaceBinding.setVisiting(this);

        parkiBeach = findViewById(R.id.parkibeach);
        tarnelRoad = findViewById(R.id.tarnel_road);
        hiltopPark = findViewById(R.id.hiltop_park);
        mennaGarden = findViewById(R.id.menna_garden);

    }

    public void showParkiBeach(){

        Intent intent = new Intent(VistingPlace.this,ShowVisitingPlace.class);
        intent.putExtra("visitng","parki beach");
        startActivity(intent);

    }

    public void showTarnelRoad(){
        Intent intent = new Intent(VistingPlace.this,ShowVisitingPlace.class);
        intent.putExtra("visitng","tunnal road");
        startActivity(intent);

    }
    public void  showHiltopPark(){
        Intent intent = new Intent(VistingPlace.this,ShowVisitingPlace.class);
        intent.putExtra("visitng","hiltop park");
        startActivity(intent);

    }
    public void  showMennaGarden(){
        Intent intent = new Intent(VistingPlace.this,ShowVisitingPlace.class);
        intent.putExtra("visitng","menna garden");
        startActivity(intent);

    }
}