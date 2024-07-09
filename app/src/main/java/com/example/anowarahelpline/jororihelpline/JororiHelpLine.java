package com.example.anowarahelpline.jororihelpline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.ActivityJororiHelpLineBinding;

public class JororiHelpLine extends AppCompatActivity {

    ActivityJororiHelpLineBinding activityJororiHelpLineBinding;
    CardView police,pollibidot,fireservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jorori_help_line);
        activityJororiHelpLineBinding = DataBindingUtil.setContentView(this,R.layout.activity_jorori_help_line);
        activityJororiHelpLineBinding.setNationalHelpline(this);


    }

    public void anowaraPollibittoth(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:01769-400102"));
        startActivity(intents);
    }

    public void anowaraPolice(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:01320107834"));
        startActivity(intents);
    }

    public void anowaraFireService(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:01842-956222"));
        startActivity(intents);
    }

    public void jatioJororiSheba(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:999"));
        startActivity(intents);
    }

    public void jatioHotLine(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:16575"));
        startActivity(intents);
    }

    public void nariOshishoNirjaton(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:109"));
        startActivity(intents);
    }

    public void jatiOTorthoSheba(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:333"));
        startActivity(intents);
    }

    public void dodok(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:106"));
        startActivity(intents);
    }

    public void sorkariAyinSheba(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:16430"));
        startActivity(intents);
    }

    public void dorshogAgamBartha(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:1090"));
        startActivity(intents);
    }

    public void bomiSheab(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:16122"));
        startActivity(intents);
    }



}