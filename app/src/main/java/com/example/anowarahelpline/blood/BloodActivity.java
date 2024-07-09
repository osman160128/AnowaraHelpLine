package com.example.anowarahelpline.blood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.anowarahelpline.R;

public class BloodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        boolean alreadyShows = sharedPreferences.getBoolean("is already shows", false);

        if(alreadyShows){
            Intent intent = new Intent(BloodActivity.this,BloodLoginActivity.class);
            startActivity(intent);
            // Optionally finish the current activity
            finish();
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Get a reference to the SharedPreferences object
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    // Get an editor to write to the SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("is already shows",true);
                    // Apply the changes
                    editor.apply();
                    // Intent to start the second activity
                    Intent intent = new Intent(BloodActivity.this,BloodActivity2.class);
                    startActivity(intent);
                    // Optionally finish the current activity
                    finish();

                }
            }, 5000);

        }

        // Handler to delay the transition
       // 5000 milliseconds = 5 seconds
    }

}