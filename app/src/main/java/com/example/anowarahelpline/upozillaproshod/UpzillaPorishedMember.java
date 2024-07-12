package com.example.anowarahelpline.upozillaproshod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.ActivityUpzillaPorishedMemberBinding;

public class UpzillaPorishedMember extends AppCompatActivity {

    ActivityUpzillaPorishedMemberBinding activityUpzillaPorishedMemberBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upzilla_porished_member);
        activityUpzillaPorishedMemberBinding = DataBindingUtil.setContentView(this, R.layout.activity_upzilla_porished_member);
        activityUpzillaPorishedMemberBinding.setUpzila(this);
    }
    public void mp(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:"+"+8801711723456"));
        startActivity(intents);

    }
    public  void mohilaMp(){

    }

    public void upzilaChirman(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:"+"+8801711386633"));
        startActivity(intents);

    }
    public void upzilabaisChirman(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:"+"+8801717117690"));
        startActivity(intents);

    }
    public void upzilaMohilaBaisChirman(){
        Intent intents = new Intent(Intent.ACTION_DIAL);
        intents.setData(Uri.parse("tel:"+"+8801734311811"));
        startActivity(intents);

    }

}