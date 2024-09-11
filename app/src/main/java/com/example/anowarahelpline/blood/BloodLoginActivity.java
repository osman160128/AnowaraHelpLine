package com.example.anowarahelpline.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BloodLoginActivity extends AppCompatActivity {

    ActivityBloodLoginBinding activityBloodLoginBinding;
    TextInputEditText edtEmail;
    TextInputEditText edtPassword;

    FirebaseAuth mAuth;
    String currentUser;
    TextView aPostiveBtn, aNegativeBtn, bPostiveBtn, bNegativeBtn, abPostiveBtn, abNegativeBtn, oPostiveBtn, oNegativeBtn;

    String bloodGroup = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_login);


        activityBloodLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_blood_login);
        activityBloodLoginBinding.setBloodlogin(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        boolean alreadyShows = sharedPreferences.getBoolean("already shows", false);

        if (alreadyShows) {
            Intent intent = new Intent(BloodLoginActivity.this, BloodShowActvity.class);
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

    public void loginFunction() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail.setError("Enter an email address");
            edtEmail.requestFocus();
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid email address");
            edtEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        else if (email.isEmpty()) {
            edtPassword.setError("Enter a password");
            edtPassword.requestFocus();
            return;
        } else if (bloodGroup.isEmpty()) {
            Toast.makeText(this, "plese select blood group", Toast.LENGTH_SHORT).show();
        } else {
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

                        currentUser = mAuth.getCurrentUser().getUid();
                        //this check the blood group
                        sendBloodGroupToProfile();

                    } else {

                        Toast.makeText(BloodLoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }

    private void sendBloodGroupToProfile() {

        if (bloodGroup.equals("A+")) {
            fetchDataFromAPosativeFireBase();
            BloodDonerProfile.bloodGroup = "A+ Bood Group";
            BloodShowActvity.bloodGroup = "A+ Bood Group";
        } else if (bloodGroup.equals("A-")) {
            fetchDataFromANegativeFireBase();
            BloodDonerProfile.bloodGroup = "A- Bood Group";
            BloodShowActvity.bloodGroup = "A- Bood Group";
        } else if (bloodGroup.equals("B+")) {
            fetchDataFromBPosativeFireBase();
            BloodDonerProfile.bloodGroup = "B+ Bood Group";
            BloodShowActvity.bloodGroup = "B+ Bood Group";
        } else if (bloodGroup.equals("B-")) {
            fetchDataFromBNeagtiveFireBase();
            BloodDonerProfile.bloodGroup = "B- Bood Group";
            BloodShowActvity.bloodGroup = "B- Bood Group";
        } else if (bloodGroup.equals("AB+")) {
            fetchDataFromABPosetiveFireBase();
            BloodDonerProfile.bloodGroup = "AB+ Bood Group";
            BloodShowActvity.bloodGroup = "AB+ Bood Group";
        } else if (bloodGroup.equals("AB-")) {
            fetchDataFromABNegativeFireBase();
            BloodDonerProfile.bloodGroup = "AB- Bood Group";
            BloodShowActvity.bloodGroup = "AB- Bood Group";
        } else if (bloodGroup.equals("O+")) {
            fetchDataFromOPostive();
            BloodDonerProfile.bloodGroup = "O+ Bood Group";
            BloodShowActvity.bloodGroup = "O+ Bood Group";
        } else if (bloodGroup.equals("O")) {
            fetchDataFromONeagtiave();
            BloodDonerProfile.bloodGroup = "O+ Bood Group";
            BloodShowActvity.bloodGroup = "O+ Bood Group";
        }
    }


    public void goToRegistration() {
        // Get a reference to the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        // Get an editor to write to the SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("already shows", true);
        // Apply the changes
        editor.apply();
        startActivity(new Intent(BloodLoginActivity.this, BloodRegistrationActivity.class));

    }

    //select blood button
    public void aPostive() {

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

    public void aNegative() {
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

    public void bPostive() {
        bloodGroup = "B+";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        ;
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);

    }

    public void bNegative() {
        bloodGroup = "B-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
        ;
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void oPostavie() {
        bloodGroup = "O+";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void oNegative() {
        bloodGroup = "O-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void abPosataive() {
        bloodGroup = "AB+";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.blood_donation);
        abNegativeBtn.setBackgroundResource(R.drawable.stoke);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    public void abNegative() {
        bloodGroup = "AB-";
        aPostiveBtn.setBackgroundResource(R.drawable.stoke);
        aNegativeBtn.setBackgroundResource(R.drawable.stoke);
        bPostiveBtn.setBackgroundResource(R.drawable.stoke);
        bNegativeBtn.setBackgroundResource(R.drawable.stoke);
        abPostiveBtn.setBackgroundResource(R.drawable.stoke);
        abNegativeBtn.setBackgroundResource(R.drawable.blood_donation);
        oPostiveBtn.setBackgroundResource(R.drawable.stoke);
        oNegativeBtn.setBackgroundResource(R.drawable.stoke);
    }

    //fetch data to check selected blood group is already tn this

    private void fetchDataFromONeagtiave() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O- Bood Group").child(currentUser);

        fetcdataFromFirebase(databaseReference);
    }

    private void fetchDataFromOPostive() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O+ Bood Group").child(currentUser);
        fetcdataFromFirebase(databaseReference);
    }

    private void fetchDataFromABNegativeFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AB- Bood Group").child(currentUser);
        fetcdataFromFirebase(databaseReference);

    }

    private void fetchDataFromABPosetiveFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AB+ Bood Group").child(currentUser);
        fetcdataFromFirebase(databaseReference);

    }

    private void fetchDataFromBNeagtiveFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B- Bood Group").child(currentUser);

        fetcdataFromFirebase(databaseReference);
    }

    private void fetchDataFromBPosativeFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B+ Bood Group").child(currentUser);
        fetcdataFromFirebase(databaseReference);
    }

    private void fetchDataFromANegativeFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A- Bood Group").child(currentUser);
        fetcdataFromFirebase(databaseReference);
    }

    private void fetchDataFromAPosativeFireBase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A+ Bood Group").child(currentUser);
        fetcdataFromFirebase(databaseReference);
    }

    private void fetcdataFromFirebase(DatabaseReference databaseReference) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(BloodLoginActivity.this, "pppppppp", Toast.LENGTH_SHORT).show();
                if (snapshot.exists()) {

                    startActivity(new Intent(BloodLoginActivity.this,BloodShowActvity.class));
                    finish();

                } else {
                    //  this alart dialog show if you choose wrong blood blodGroup;
                    //and show alart to login again;

                    showALartDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BloodLoginActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void showALartDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BloodLoginActivity.this);
        builder.setTitle("Hello Anowra");
        builder.setMessage("আপনি ভুল রক্তের গ্রুপ নির্বাচন করেছেন। অনুগ্রহ করে আবার লগইন করুন");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mAuth.signOut();
                // Handle positive button click
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}