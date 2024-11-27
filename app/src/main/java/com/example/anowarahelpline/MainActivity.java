package com.example.anowarahelpline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearSmoothScroller;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
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
import com.example.anowarahelpline.news.NewsReporter;
import com.example.anowarahelpline.resturant.ResturantAcitivity;
import com.example.anowarahelpline.upozillaproshod.UnionProsihod;
import com.example.anowarahelpline.upozillaproshod.UpzillaPorishedMember;
import com.example.anowarahelpline.us.JogajogwithUs;
import com.example.anowarahelpline.visitingplace.VistingPlace;
import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView chattogramHelpLine;
    Animation chattogramHelpLineAnimatio;
    ImageView ctgBeutifulPlace;
    private ViewPager viewPager;

    RecyclerView animationImageRecylerView;
    private ImageAnimationAdapter imageAnimationAdapter;

    LinearLayoutManager layoutManager;

    //LinerLayout
    LinearLayout hospitalBtn,doctorBtn,ambulanceBtn,proshahonBtn,medicineBtn,pallibidyutBtn,
            newsReporter,newsPaperBtn,trainTicketBtn,bloodBtn,busTicketBtn,jororiHelpLine,resturantBtn,hotelBtn,
            gariRant,visitingPlaceBtn,jonprotinidi,unionPorishodBtn,jogajogWithUsBtn,emargancyBtn;

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

        animationImageRecylerView = findViewById(R.id.animationImageRecylerView);
        imageAnimationAdapter = new ImageAnimationAdapter(this,imageResources);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        animationImageRecylerView.setLayoutManager(layoutManager);
        animationImageRecylerView.setAdapter(imageAnimationAdapter);

        MovieCoverAutoScrolling();


        //findView work here
        hospitalBtn = findViewById(R.id.txtHospital);
        doctorBtn = findViewById(R.id.txtDr);
        ambulanceBtn = findViewById(R.id.txtAmbulance);

        newsReporter = findViewById(R.id.txtSongbadik);

        bloodBtn = findViewById(R.id.txtBlood);
        jororiHelpLine = findViewById(R.id.txtHalpeLine);
        resturantBtn = findViewById(R.id.txtResturant);
        hotelBtn = findViewById(R.id.txtHotel);
        gariRant = findViewById(R.id.txtCar);
        visitingPlaceBtn=findViewById(R.id.txtDorshonoiyoJaiga);
        jonprotinidi = findViewById(R.id.jatioHelpCenter);
        unionPorishodBtn = findViewById(R.id.unionPorishod);
        jogajogWithUsBtn = findViewById(R.id.jogagjogWithUs);
        emargancyBtn = findViewById(R.id.emargancyHotlinebtn);

        //cliklistener add
        hospitalBtn.setOnClickListener(this);
        doctorBtn.setOnClickListener(this);
        ambulanceBtn.setOnClickListener(this);

        newsReporter.setOnClickListener(this);

        bloodBtn.setOnClickListener(this);
        jororiHelpLine.setOnClickListener(this);
        resturantBtn.setOnClickListener(this);
        hotelBtn.setOnClickListener(this);
        gariRant.setOnClickListener(this);
        visitingPlaceBtn.setOnClickListener(this);
        jonprotinidi.setOnClickListener(this);
        unionPorishodBtn.setOnClickListener(this);
        jogajogWithUsBtn.setOnClickListener(this);
        emargancyBtn.setOnClickListener(this);


        //make the heading linganimaition
        TextView movingTextView = findViewById(R.id.chottogramHelpLine2);


        // Get screen width
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        // Set the initial position of the text to be off-screen on the right
        movingTextView.setX(screenWidth);

        // Animation logic for right to left movement of heading
        ValueAnimator animator = ValueAnimator.ofFloat(screenWidth, -700);
        animator.setDuration(8000); // Duration for one full movement
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE); // Infinite loop for right to left
        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            movingTextView.setX(animatedValue);
        });

        // Start the animation
        animator.start();

    }   //   onCreate() mthod ending backet



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

        else if (v.getId()==R.id.txtSongbadik) {
            Intent intent = new Intent(MainActivity.this, NewsReporter.class);
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
        } else if (v.getId()==R.id.jogagjogWithUs) {
            Intent intent = new Intent(MainActivity.this, JogajogwithUs.class);
            startActivity(intent);
        }else if (v.getId()==R.id.emargancyHotlinebtn) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:999"));
            startActivity(intent);
        }
        else if (v.getId()==R.id.txtCar) {
            Intent intent = new Intent(MainActivity.this, GarirentActivity.class);
            startActivity(intent);
        }
    }

    private void MovieCoverAutoScrolling() {
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(animationImageRecylerView);

        Timer timer = new Timer();
        final boolean[] scrollToEnd = {true};

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = imageAnimationAdapter.getItemCount();

                if (scrollToEnd[0]) {
                    if (lastVisibleItemPosition < itemCount - 1) {
                        smoothScrollToPosition(lastVisibleItemPosition + 1, itemCount);
                    } else {
                        // Change direction to scroll from end to start
                        scrollToEnd[0] = false;
                    }
                } else {
                    if (lastVisibleItemPosition > 0) {
                        smoothScrollToPosition(lastVisibleItemPosition - 1, itemCount);
                    } else {
                        // Change direction to scroll from start to end
                        scrollToEnd[0] = true;
                    }
                }
            }
        }, 0, 3000);
    }

    private void smoothScrollToPosition(int targetPosition, int itemCount) {
        if (targetPosition >= 0 && targetPosition < itemCount) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(MainActivity.this) {
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    // Adjust this value to control the speed of scrolling
                    return 0.4f;
                }
            };

            smoothScroller.setTargetPosition(targetPosition);
            layoutManager.startSmoothScroll(smoothScroller);
        }
    }
}
