package com.example.anowarahelpline.blood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anowarahelpline.R;

public class BloodActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood3);
        Button button = findViewById(R.id.blood_image_3_continue);
        SharedPreferences sharedPreferences = getSharedPreferences("BloodSharedPref", MODE_PRIVATE);

        boolean alreadyShows = sharedPreferences.getBoolean("is already shows", false);
        //if install app firstime it will show this activity
        //other wise it hide
        if(alreadyShows){
            Intent intent = new Intent(BloodActivity3.this,BloodLoginActivity.class);
            startActivity(intent);
            // Optionally finish the current activity
            finish();
        }else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Intent to start the second activity
                    startActivity(new Intent(BloodActivity3.this,BloodActivity4.class));
                    finish();
                }
            });
        }

    }
}