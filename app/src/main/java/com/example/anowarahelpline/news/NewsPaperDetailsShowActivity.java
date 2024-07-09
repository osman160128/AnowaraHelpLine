package com.example.anowarahelpline.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.anowarahelpline.R;

public class NewsPaperDetailsShowActivity extends AppCompatActivity {

    ProgressBar newspaperDetilsShowProgressbar;
    WebView newspaperDetilsShowWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_paper_details_show);

        newspaperDetilsShowProgressbar = findViewById(R.id.newspaperDetailsShowProgressBar);
        newspaperDetilsShowWebView = findViewById(R.id.newspaperDetailsShowWebView);
        String data = getIntent().getStringExtra("url");

        newspaperDetilsShowWebView.loadUrl(data);
        newspaperDetilsShowWebView.setWebViewClient(new WebViewClient() {

        });
    }

    @Override
    public void onBackPressed() {

        if (newspaperDetilsShowWebView.canGoBack()) {
            newspaperDetilsShowWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
