package com.example.anowarahelpline.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anowarahelpline.MainActivity;
import com.example.anowarahelpline.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;



public class BloodShowActvity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    CircleImageView imgView;
    TextView txtName;
    FragmentManager fragmentManager = getSupportFragmentManager();

    public static String bloodGroup="";
    FirebaseAuth mAuth;
    String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_show_actvity);

        if(bloodGroup.isEmpty()){
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

            bloodGroup = sharedPreferences.getString("bloodGroup","");

        }
        else {
            // Get a reference to the SharedPreferences object
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            // Get an editor to write to the SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("bloodGroup", bloodGroup);
            // Apply the changes
            editor.apply();


        }

        drawerLayout = findViewById(R.id.bloodDonerDrawerLayout);
        materialToolbar = findViewById(R.id.bloodDonerToolBarLayout);
        frameLayout = findViewById(R.id.bloodDonerFrameLyout);
        navigationView = findViewById(R.id.bloodDonerNavigationView);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();


        imgView =  navigationView.getHeaderView(0).findViewById(R.id.headerImage);
        txtName =  navigationView.getHeaderView(0).findViewById(R.id.headerName);


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bloodDonerFrameLyout,new ShowAllBloodFragment());
        fragmentTransaction.commit();

        //get data from firebase according to blood group

        fetchDonerInfo();


        ActionBarDrawerToggle toggl = new ActionBarDrawerToggle(
                BloodShowActvity.this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open
        );

        drawerLayout.addDrawerListener(toggl);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.navigationHome){
                    //show all blood doner togather fragment
                    FragmentTransaction fragmentTransaction1  =  fragmentManager.beginTransaction();
                    fragmentTransaction1.replace(R.id.bloodDonerFrameLyout,new ShowAllBloodFragment());
                    fragmentTransaction1.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId()==R.id.navigationProfile) {
                    //prfile fragment
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    fragmentTransaction1.replace(R.id.bloodDonerFrameLyout, new BloodDonerProfile());
                    fragmentTransaction1.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId()==R.id.navigationHitory) {
                    //prfile fragment
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    fragmentTransaction1.replace(R.id.bloodDonerFrameLyout, new BloodHistoryFragment());
                    fragmentTransaction1.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }



                return true;
            }
        });
    }

    private void fetchDonerInfo() {
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
                Toast.makeText(BloodShowActvity.this, "pppppppp", Toast.LENGTH_SHORT).show();
                if (snapshot.exists()){

                    BloodGroupModel bloodGroupModel = snapshot.getValue(BloodGroupModel.class);

                    String name = bloodGroupModel.getName();
                    String imgUrl = bloodGroupModel.getDownloadImgUri();

                    Log.d("osman",name);
                    Log.d("osman",imgUrl);

                    txtName.setText(name);;


                    Picasso.get()
                            .load(imgUrl)
                            .fit()
                            .centerCrop()
                            .into(imgView);

                }
                else {
                    Toast.makeText(BloodShowActvity.this, "not exisit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BloodShowActvity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}