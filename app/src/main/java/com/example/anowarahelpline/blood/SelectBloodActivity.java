package com.example.anowarahelpline.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.ActivitySelectBloodBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SelectBloodActivity extends AppCompatActivity {

    ActivitySelectBloodBinding activitySelectBloodBinding;
    TextView aPostiveBtn,aNegativeBtn,bPostiveBtn,bNegativeBtn,abPostiveBtn,abNegativeBtn,oPostiveBtn,oNegativeBtn;
    String bloodGroup;
    String name,mobileNumber,password,imgUri,email;
    StorageReference storageReference;

    FirebaseAuth mAuth;
    String currentUser;

    Uri downloadImgUri;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_blood);

        activitySelectBloodBinding = DataBindingUtil.setContentView(this,R.layout.activity_select_blood);
        activitySelectBloodBinding.setSelectblood(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        storageReference = FirebaseStorage.getInstance().getReference();

        aPostiveBtn = findViewById(R.id.aPostiveBtn);
        aNegativeBtn = findViewById(R.id.aNegativeBtn);
        bPostiveBtn = findViewById(R.id.bPostiveBtn);
        bNegativeBtn = findViewById(R.id.bNegativeBtn);
        abPostiveBtn = findViewById(R.id.abPostiveBtn);
        abNegativeBtn = findViewById(R.id.abNegativeBtn);
        oPostiveBtn = findViewById(R.id.oPostiveBtn);
        oNegativeBtn = findViewById(R.id.oNegativeBtn);
        progressBar = findViewById(R.id.select_blood_progressbar);



        //make invisable progressbar
        progressBar.setVisibility(View.GONE);

        Intent reciveIntent = getIntent();

        if(reciveIntent!=null) {
            name = reciveIntent.getStringExtra("name");
            mobileNumber = reciveIntent.getStringExtra("mobileNumber");
            password = reciveIntent.getStringExtra("password");
            email = reciveIntent.getStringExtra("email");
            imgUri = reciveIntent.getStringExtra("imgUri");

        }



    }


    public void aPostive(){

        bloodGroup = "A+";
        aPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));


    }

    public void aNegative(){
        bloodGroup = "A-";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));

    }

    public void bPostive(){
        bloodGroup = "B+";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundResource(R.drawable.blood_donation);;
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));

    }
    public void bNegative(){
        bloodGroup="B-";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundResource(R.drawable.blood_donation);;
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
    }

    public void oPostavie(){
        bloodGroup="O+";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
    }

    public void oNegative(){
        bloodGroup="O-";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
    }

    public void abPosataive(){
        bloodGroup="AB+";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        abNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
    }

    public void abNegative(){
        bloodGroup="AB-";
        aPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        aNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        bNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        abNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
        oPostiveBtn.setBackgroundColor(Color.parseColor("#EC1313"));
        oNegativeBtn.setBackgroundColor(Color.parseColor("#EC1313"));
    }

    public void sigingBtnClick(){

        progressBar.setVisibility(View.VISIBLE);

        if (bloodGroup == null || bloodGroup.isEmpty()) {
            Toast.makeText(this, "Please select your blood group", Toast.LENGTH_SHORT).show();
            return;
        }else {

                        uploadImgToFirebase();

        }


    }


    //upload image to firebase sotrage
    private void uploadImgToFirebase() {

        StorageReference ref = storageReference.child(mAuth.getCurrentUser().getUid() + "." + getFileExtension(Uri.parse(imgUri)));;

        ref.putFile(Uri.parse(imgUri)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downloadImgUri  = uri;

                        if (bloodGroup.equals("A+")) {
                            BloodDonerProfile.bloodGroup="A+ Bood Group";
                            BloodShowActvity.bloodGroup="A+ Bood Group";

                            sendDataToAPosativeFireBase();
                        } else if (bloodGroup.equals("A-")) {
                            BloodDonerProfile.bloodGroup="A- Bood Group";
                            BloodShowActvity.bloodGroup="A- Bood Group";
                            sendDataToANegativeFireBase();
                        } else if (bloodGroup.equals("B+")) {
                            BloodDonerProfile.bloodGroup="B+ Bood Group";
                            BloodShowActvity.bloodGroup="B+ Bood Group";
                            sendDataToBPosativeFireBase();
                        } else if (bloodGroup.equals("B-")) {
                            BloodDonerProfile.bloodGroup="B- Bood Group";
                            BloodShowActvity.bloodGroup="B- Bood Group";
                            sendDataToBNeagtiveFireBase();
                        } else if (bloodGroup.equals("AB+")) {
                            BloodDonerProfile.bloodGroup="AB+ Bood Group";
                            BloodShowActvity.bloodGroup="AB+ Bood Group";
                            sendDataToABPosetiveFireBase();
                        } else if (bloodGroup.equals("AB-")) {
                            BloodDonerProfile.bloodGroup="AB- Bood Group";
                            BloodShowActvity.bloodGroup="AB- Bood Group";
                            sendDataToABNegativeFireBase();
                        } else if (bloodGroup.equals("O+")) {
                            BloodDonerProfile.bloodGroup="O+ Bood Group";
                            BloodShowActvity.bloodGroup="O+ Bood Group";
                            sendDataToOPostive();
                        } else if (bloodGroup.equals("O")) {
                            BloodDonerProfile.bloodGroup="O- Bood Group";
                            BloodShowActvity.bloodGroup="O- Bood Group";
                            sendDataToONeagtiave();
                        }
                    }
                });

            }
        });

    }

    private void sendDataToABNegativeFireBase() {

        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AB- Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToABPosetiveFireBase() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AB+ Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToBNeagtiveFireBase() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B- Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToBPosativeFireBase() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B+ Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToANegativeFireBase() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A- Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToAPosativeFireBase() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A+ Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToONeagtiave() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O- Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }

    private void sendDataToOPostive() {
        BloodGroupModel bloodGroupModel = new BloodGroupModel(name,mobileNumber,email,imgUri,bloodGroup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O+ Bood Group").child(currentUser);
        databaseReference.setValue(bloodGroupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SelectBloodActivity.this,BloodShowActvity.class));
                }
            }
        });
    }


    //get the extension name of image
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}