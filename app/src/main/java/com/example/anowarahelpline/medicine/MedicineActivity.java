package com.example.anowarahelpline.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.anowarahelpline.R;

public class MedicineActivity extends AppCompatActivity {

    WebView medicineWebView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        medicineWebView = findViewById(R.id.medicineWeb);
        progressBar = findViewById(R.id.medicine_progressbar);

        medicineWebView.loadUrl("https://medex.com.bd/");
        medicineWebView.setWebViewClient(new WebViewClient(){
        });
    }

    @Override
    public void onBackPressed() {

        if(medicineWebView.canGoBack()){
            medicineWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

}