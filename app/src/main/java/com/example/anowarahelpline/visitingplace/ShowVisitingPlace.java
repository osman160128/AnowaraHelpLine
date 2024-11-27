package com.example.anowarahelpline.visitingplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anowarahelpline.R;

public class ShowVisitingPlace extends AppCompatActivity {

    ImageView imageView;
    TextView txtTitle,textView;


    String visitingPlace ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_visiting_place);

        imageView = findViewById(R.id.showVisitngImg);
        textView = findViewById(R.id.showVisitingText);
        txtTitle = findViewById(R.id.showVisitngTitle);

        Intent intent = getIntent();

        if(intent!=null) {

            visitingPlace = intent.getStringExtra("visitng");

        }

        if (visitingPlace.equals("parki beach")){
            imageView.setImageResource(R.drawable.parkibeach);
            textView.setText(""+VisitingPlaceInfo.parkiBeach);
            txtTitle.setText("পারকি সমুদ্র সৈকত");

        } else if (visitingPlace.equals("tunnal road")) {
            imageView.setImageResource(R.drawable.tunnel);
            txtTitle.setText("কর্ণফুলী টানেল");

        }
        else if (visitingPlace.equals("hiltop park")) {
            imageView.setImageResource(R.drawable.mannagarden);
            textView.setText(""+VisitingPlaceInfo.hiltoPark);
            txtTitle.setText("হিলটপ পার্ক");

        }
        else if (visitingPlace.equals("menna garden")) {
            imageView.setImageResource(R.drawable.mennagarden);
            txtTitle.setText("মেন্না গার্ডেন ");

        }else if (visitingPlace.equals("mazar")) {
            imageView.setImageResource(R.drawable.mazarshorif);
            txtTitle.setText("হযরত শাহ মোহছেন আউলিয়া (রহঃ) মাজার");
            textView.setText(""+VisitingPlaceInfo.mazar);

        } else if (visitingPlace.equals("mosjid")) {
            imageView.setImageResource(R.drawable.chorimosjid);
            txtTitle.setText("ঐতিহাসিক ছুরুত বিবি মসজিদ");
            textView.setText(""+VisitingPlaceInfo.chorothbibimosijid);

        }


    }
}