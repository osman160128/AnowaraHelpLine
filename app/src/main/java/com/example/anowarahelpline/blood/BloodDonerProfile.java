package com.example.anowarahelpline.blood;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anowarahelpline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class BloodDonerProfile extends Fragment {

    public static String bloodGroup ="";
    FirebaseAuth mAuth;
    String currentUser;
    TextView txtName,txtBloodGroup,txtMobileNumber,txtUpozila,txtEmail,bloodDonateDate;
    String setName,setBloodGroup,setMobileNumber,setUpozila,setEmail,setImage,setBlooDOnateDate;
    CircleImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blood_doner_profile, container, false);

        //if proifle fragment open this get the blood group save for next time
        //otherwise after first time open will show blood group is empty
        if(bloodGroup.isEmpty()){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

            bloodGroup = sharedPreferences.getString("bloodGroup","");

        }
        else {
            // Get a reference to the SharedPreferences object
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            // Get an editor to write to the SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("bloodGroup", bloodGroup);
            // Apply the changes
            editor.apply();


        }



        txtName = view.findViewById(R.id.blodDonerProfileName);
        txtBloodGroup = view.findViewById(R.id.blodDonerprofileBLoodGroup);
        txtMobileNumber = view.findViewById(R.id.blodDonerprofilePhone);
        txtEmail = view.findViewById(R.id.blodDonerprofileEmail);
        imageView = view.findViewById(R.id.blodDonerProfileImg);

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();

        Toast.makeText(getContext(), ""+bloodGroup, Toast.LENGTH_SHORT).show();

        if (bloodGroup.equals("A+ Bood Group")) {
            fetchDataFromAPosativeFireBase();
        } else if (bloodGroup.equals("A- Bood Group")) {
            fetchDataFromANegativeFireBase();
        } else if (bloodGroup.equals("B+ Bood Group")) {
            fetchDataFromBPosativeFireBase();
        } else if (bloodGroup.equals("B- Bood Group")) {
            fetchDataFromBNeagtiveFireBase();
        } else if (bloodGroup.equals("AB+ Bood Group")) {
            fetchDataFromABPosetiveFireBase();
        } else if (bloodGroup.equals("AB- Bood Group")) {
            fetchDataFromABNegativeFireBase();
        } else if (bloodGroup.equals("O+ Bood Group")) {
            fetchDataFromOPostive();
        } else if (bloodGroup.equals("O- Bood Group")) {
            fetchDataFromONeagtiave();
        }
        return view;
    }

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
                Toast.makeText(getContext(), "pppppppp", Toast.LENGTH_SHORT).show();
                if (snapshot.exists()){


                        Toast.makeText(getContext(), "exisit too", Toast.LENGTH_SHORT).show();
                        BloodGroupModel bloodGroupModel = snapshot.getValue(BloodGroupModel.class);

                        String name = bloodGroupModel.getName();
                        String bloodGroup = bloodGroupModel.getBloodGroup();
                        String  email = bloodGroupModel.getEmail();
                        String imgUrl = bloodGroupModel.getDownloadImgUri();
                        String mobileNumber = bloodGroupModel.getMobileNumber();

                        Log.d("img",imgUrl);
                        if(imgUrl.isEmpty()){
                            Toast.makeText(getContext(), "Img is empty" , Toast.LENGTH_SHORT).show();
                        }

                        txtName.setText(name);
                        txtBloodGroup.setText(bloodGroup);
                        txtEmail.setText(email);
                        txtMobileNumber.setText(mobileNumber);

                    Picasso.get()
                            .load(imgUrl)
                            .fit()
                            .centerCrop()
                            .into(imageView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}