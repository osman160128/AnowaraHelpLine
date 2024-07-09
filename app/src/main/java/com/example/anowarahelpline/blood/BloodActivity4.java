package com.example.anowarahelpline.blood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anowarahelpline.R;

public class BloodActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood4);

        Button button = findViewById(R.id.blood_image_4_continue);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodActivity4.this,BloodLoginActivity.class));
                finish();
            }
        });
    }
}