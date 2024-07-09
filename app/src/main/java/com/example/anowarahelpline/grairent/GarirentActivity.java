package com.example.anowarahelpline.grairent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.anowarahelpline.R;

public class GarirentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garirent);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Anowara Help Line");
        alertDialog.setMessage("তথ্য দিয়ে সাহায্য করুন ");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNumber = "+8801873148453";

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
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialogs = alertDialog.create();
        // Show the AlertDialog
        alertDialogs.show();
    }
}