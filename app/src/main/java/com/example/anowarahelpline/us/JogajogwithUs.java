package com.example.anowarahelpline.us;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.anowarahelpline.R;

import org.w3c.dom.Text;

public class JogajogwithUs extends AppCompatActivity {

    String info = "আপনার এলাকার যেকোনো তথ্য দিতে এবং এই অ্যাপের অভিযোগ জানাতে নিচে ক্লিক করুন ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogajogwith_us);

        TextView jogajogTxt = findViewById(R.id.jogajogText);
        TextView jogajogBtn = findViewById(R.id.jogajogBtn);

        jogajogTxt.setText(info);


        jogajogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+8801892037702";

                // Create an intent with the action ACTION_VIEW
                Intent intent = new Intent(Intent.ACTION_VIEW);

                // Set the data of the intent to a WhatsApp URI with the phone number
                Uri uri = Uri.parse("https://wa.me/" + phoneNumber);

                // Set the URI as the data of the intent
                intent.setData(uri);

                // Start the intent
                startActivity(intent);
            }
        });

    }
}