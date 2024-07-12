package com.example.anowarahelpline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anowarahelpline.ambulance.AmbulanceAcitivity;
import com.example.anowarahelpline.blood.BloodActivity;
import com.example.anowarahelpline.grairent.GarirentActivity;
import com.example.anowarahelpline.hospital.DoctoorsActivity;
import com.example.anowarahelpline.hospital.HospitalActivity;
import com.example.anowarahelpline.hotel.HotelActivity;
import com.example.anowarahelpline.jororihelpline.JororiHelpLine;
import com.example.anowarahelpline.medicine.MedicineActivity;
import com.example.anowarahelpline.news.NewsActivity;
import com.example.anowarahelpline.news.NewsReporter;
import com.example.anowarahelpline.resturant.ResturantAcitivity;
import com.example.anowarahelpline.upozillaproshod.UnionProsihod;
import com.example.anowarahelpline.upozillaproshod.UpzillaPorishedMember;
import com.example.anowarahelpline.visitingplace.VistingPlace;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView chattogramHelpLine;
    Animation chattogramHelpLineAnimatio;
    ImageView ctgBeutifulPlace;
    private ViewPager viewPager;
    private ImagePagerAdapter pagerAdapter;
    private Handler handler;
    private Runnable runnable;
    private int delayTime = 2000; // Delay between image transitions in milliseconds
    private int currentPage = 0;


    //LinerLayout
    LinearLayout hospitalBtn,doctorBtn,ambulanceBtn,proshahonBtn,medicineBtn,pallibidyutBtn,
            newsReporter,newsPaperBtn,trainTicketBtn,bloodBtn,busTicketBtn,jororiHelpLine,resturantBtn,hotelBtn,
            gariRant,visitingPlaceBtn,jonprotinidi,unionPorishodBtn;

    private int[] imageResources = {
            R.drawable.img_0,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,
            R.drawable.img_9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chattogramHelpLine = findViewById(R.id.chottogramHelpLine2);
        chattogramHelpLineAnimatio = AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left);

        //animate beutiful of chittagong
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new ImagePagerAdapter(this, imageResources);
        viewPager.setAdapter(pagerAdapter);
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (currentPage == imageResources.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, delayTime);
            }
        };

        //findView work here
        hospitalBtn = findViewById(R.id.txtHospital);
        doctorBtn = findViewById(R.id.txtDr);
        ambulanceBtn = findViewById(R.id.txtAmbulance);
        medicineBtn = findViewById(R.id.txtMadicine);
        newsReporter = findViewById(R.id.txtSongbadik);
        newsPaperBtn= findViewById(R.id.txtNews);
        bloodBtn = findViewById(R.id.txtBlood);
        jororiHelpLine = findViewById(R.id.txtHalpeLine);
        resturantBtn = findViewById(R.id.txtResturant);
        hotelBtn = findViewById(R.id.txtHotel);
        gariRant = findViewById(R.id.txtCar);
        visitingPlaceBtn=findViewById(R.id.txtDorshonoiyoJaiga);
        jonprotinidi = findViewById(R.id.jatioHelpCenter);
        unionPorishodBtn = findViewById(R.id.unionPorishod);

        //cliklistener add
        hospitalBtn.setOnClickListener(this);
        doctorBtn.setOnClickListener(this);
        ambulanceBtn.setOnClickListener(this);
        medicineBtn.setOnClickListener(this);
        newsReporter.setOnClickListener(this);
        newsPaperBtn.setOnClickListener(this);
        bloodBtn.setOnClickListener(this);
        jororiHelpLine.setOnClickListener(this);
        resturantBtn.setOnClickListener(this);
        hotelBtn.setOnClickListener(this);
        gariRant.setOnClickListener(this);
        visitingPlaceBtn.setOnClickListener(this);
        jonprotinidi.setOnClickListener(this);
        unionPorishodBtn.setOnClickListener(this);



    }   //   onCreate() mthod ending backet

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delayTime);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.txtHospital){
            Intent intent = new Intent(MainActivity.this, HospitalActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtDr){
            Intent intent = new Intent(MainActivity.this, DoctoorsActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtAmbulance){
            Intent intent = new Intent(MainActivity.this, AmbulanceAcitivity    .class);
            startActivity(intent);
        }

        else if (v.getId()==R.id.txtMadicine){
            Intent intent = new Intent(MainActivity.this, MedicineActivity.class);
            startActivity(intent);
        }

        else if (v.getId()==R.id.txtSongbadik) {
            Intent intent = new Intent(MainActivity.this, NewsReporter.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtNews) {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtBlood) {
            Intent intent = new Intent(MainActivity.this, BloodActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtHalpeLine) {
            Intent intent = new Intent(MainActivity.this, JororiHelpLine.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtResturant) {
            Intent intent = new Intent(MainActivity.this, ResturantAcitivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtHotel) {
            Intent intent = new Intent(MainActivity.this, HotelActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtHotel) {
            Intent intent = new Intent(MainActivity.this, HotelActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtCar) {
            Intent intent = new Intent(MainActivity.this, GarirentActivity.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtDorshonoiyoJaiga) {
            Intent intent = new Intent(MainActivity.this, VistingPlace.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.jatioHelpCenter) {
            Intent intent = new Intent(MainActivity.this, UpzillaPorishedMember.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.unionPorishod) {
            Intent intent = new Intent(MainActivity.this, UnionProsihod.class);
            startActivity(intent);
        }
    }
}
