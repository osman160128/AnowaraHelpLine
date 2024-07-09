package com.example.anowarahelpline.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.ActivityBloodLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class BloodLoginActivity extends AppCompatActivity {

    ActivityBloodLoginBinding activityBloodLoginBinding;
    TextInputEditText edtEmail;
    TextInputEditText edtPassword;

    FirebaseAuth mAuth;
    TextView aPostiveBtn,aNegativeBtn,bPostiveBtn,bNegativeBtn,abPostiveBtn,abNegativeBtn,oPostiveBtn,oNegativeBtn;

    String bloodGroup="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_login);


        activityBloodLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_blood_login);
        activityBloodLoginBinding.setBloodlogin(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        boolean alreadyShows = sharedPreferences.getBoolean("already shows", false);

        if(alreadyShows){
            Intent intent = new Intent(BloodLoginActivity.this,BloodShowActvity.class);
            startActivity(intent);
            // Optionally finish the current activity
            finish();
        }

        //blood group select findview
        aPostiveBtn = findViewById(R.id.bloodLoginApositive);
        aNegativeBtn = findViewById(R.id.bloodLoginAneagtive);
        bPostiveBtn = findViewById(R.id.bloodLoginBpositive);
        bNegativeBtn = findViewById(R.id.bloodLoginBneagtive);
        abPostiveBtn = findViewById(R.id.bloodLoginABpositive);
        abNegativeBtn = findViewById(R.id.bloodLoginABneagtive);
        oPostiveBtn = findViewById(R.id.bloodLoginOpositive);
        oNegativeBtn = findViewById(R.id.bloodLoginOneagtive);
        edtEmail = findViewById(R.id.bloodLign_email);
        edtPassword = findViewById(R.id.bloodLignPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    public void loginFunction(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            edtEmail.setError("Enter an email address");
            edtEmail.requestFocus();
            return;
        }

        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Enter a valid email address");
            edtEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        else if(email.isEmpty())
        {
            edtPassword.setError("Enter a password");
            edtPassword.requestFocus();
            return;
        }
        else if(bloodGroup.isEmpty()){
            Toast.makeText(this, "plese select blood group", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        // Get a reference to the SharedPreferences object
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        // Get an editor to write to the SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("already shows", true);
                        // Apply the changes
                        editor.apply();

                        sendBloodGroupToProfile();

                        Intent intent = new Intent(BloodLoginActivity.this, BloodShowActvity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(BloodLoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }

    private void sendBloodGroupToProfile() {

        if (bloodGroup.equals("A+")) {
            BloodDonerProfile.bloodGroup = "A+ Bood Group";
            BloodShowActvity.bloodGroup = "A+ Bood Group";
        } else if (bloodGroup.equals("A-")) {
            BloodDonerProfile.bloodGroup = "A- Bood Group";
            BloodShowActvity.bloodGroup = "A- Bood Group";
        } else if (bloodGroup.equals("B+")) {
            BloodDonerProfile.bloodGroup = "B+ Bood Group";
            BloodShowActvity.bloodGroup = "B+ Bood Group";
        } else if (bloodGroup.equals("B-")) {
            BloodDonerProfile.bloodGroup = "B- Bood Group";
            BloodShowActvity.bloodGroup = "B- Bood Group";
        } else if (bloodGroup.equals("AB+")) {
            BloodDonerProfile.bloodGroup = "AB+ Bood Group";
            BloodShowActvity.bloodGroup = "AB+ Bood Group";
        } else if (bloodGroup.equals("AB-")) {
            BloodDonerProfile.bloodGroup = "AB- Bood Group";
            BloodShowActvity.bloodGroup = "AB- Bood Group";
        } else if (bloodGroup.equals("O+")) {
            BloodDonerProfile.bloodGroup = "O+ Bood Group";
            BloodShowActvity.bloodGroup = "O+ Bood Group";
        } else if (bloodGroup.equals("O")) {
            BloodDonerProfile.bloodGroup = "O+ Bood Group";
            BloodShowActvity.bloodGroup = "O+ Bood Group";
        }
    }




    public void goToRegistration(){
        // Get a reference to the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        // Get an editor to write to the SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("already shows",true);
        // Apply the changes
        editor.apply();
        startActivity(new Intent(BloodLoginActivity.this, BloodRegistrationActivity.class));
    }

    //select blood button
    public void aPostive(){

        bloodGroup = "A+";
        aPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);


    }

    public void aNegative(){
        bloodGroup = "A-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);

    }

    public void bPostive(){
        bloodGroup = "B+";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.blood_donation);;
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);

    }
    public void bNegative(){
        bloodGroup="B-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.blood_donation);;
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void oPostavie(){
        bloodGroup="O+";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void oNegative(){
        bloodGroup="O-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void abPosataive(){
        bloodGroup="AB+";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void abNegative(){
        bloodGroup="AB-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }
}