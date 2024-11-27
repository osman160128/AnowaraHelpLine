package com.example.anowarahelpline.blood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.FragmentBloodShowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BloodShowFragment extends Fragment {
    FragmentBloodShowBinding fragmentBloodShowBinding;

    FirebaseAuth mAuth;
    String currentUser;
    ArrayList<BloodGroupModel> allBloodTypeDoner = new ArrayList<>();
    ArrayList<BloodGroupModel> aPostiveArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> aNegativeArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> bPostiveArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> bNegativeArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> abPostiveArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> abegativeArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> oPostiveArrayList = new ArrayList<>();
    ArrayList<BloodGroupModel> oNegativeArrayList = new ArrayList<>();

    //boolen for check blood group
    boolean aPositiveClik = false;
    boolean aNegativeClik = false;
    boolean bPositiveClik = false;
    boolean bNegativeClik = false;
    boolean abPositiveClik = false;
    boolean abNegativeClik = false;
    boolean oPositiveClik = false;
    boolean oNegativeClik = false;
    boolean allBloodClick = false;
    BloodDonerAdapter bloodDonerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentBloodShowBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_blood_show,container,false);
        fragmentBloodShowBinding.setBloodshowfragment(this);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        //recylerview set fix size and set layout
        fragmentBloodShowBinding.showBloodRecylerViewFragment.setHasFixedSize(true);
        fragmentBloodShowBinding.showBloodRecylerViewFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        //fetch all the donerlist together
        fetchAllDOnerList();


        //search view for serac vai name
        fragmentBloodShowBinding.bloodSearcView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!allBloodTypeDoner.isEmpty()){
                    searchDoner(newText);
                }

                return false;
            }
        });



        return fragmentBloodShowBinding.getRoot();
    }

    //search blood doner
    private void searchDoner(String newText) {
        ArrayList<BloodGroupModel> searchArrayFrom = new ArrayList<>();
        ArrayList<BloodGroupModel> storeSearchArray = new ArrayList<>();
        if(aPositiveClik){
            searchArrayFrom = aPostiveArrayList;
        } else if (aNegativeClik) {
            searchArrayFrom= aNegativeArrayList;
        }else if(bPositiveClik){
            searchArrayFrom = bPostiveArrayList;
        } else if (bNegativeClik) {
             searchArrayFrom = bNegativeArrayList;
        }else if(abPositiveClik){
            searchArrayFrom = abPostiveArrayList;
        } else if (abNegativeClik) {
            searchArrayFrom = abegativeArrayList;
        }else if(oPositiveClik){
            searchArrayFrom = oPostiveArrayList;
        } else if (oNegativeClik) {
            searchArrayFrom = oNegativeArrayList;
        }else {
            searchArrayFrom = allBloodTypeDoner;
        }

        for (BloodGroupModel bloodGroupModel: searchArrayFrom){
            if((bloodGroupModel.getName().toLowerCase().contains(newText.toLowerCase()) || (bloodGroupModel.getBloodGroup().toLowerCase().contains(newText.toLowerCase())))){
                storeSearchArray.add(bloodGroupModel);
            }
        }

        if(storeSearchArray.isEmpty()){
            Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
        }else {
            bloodDonerAdapter.setSearchList(storeSearchArray);
        }

    }

    private void fetchAllDOnerList() {
        allBloodTypeDoner.clear();
        fetchApositiveDoner();
        fetchAnegativeDoner();
        fetchBpositiveDoner();
        fetchBnegativeDoner();
        fetchABpositiveDoner();
        fetchABnegativeDoner();
        fetchOpositiveDoner();
        fetchOnegativeDoner();

        Collections.shuffle(allBloodTypeDoner);
        bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
        fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
        bloodDonerAdapter.notifyDataSetChanged();

    }

    public void aPostiveClicked(){
        if(!aPositiveClik){
            aPositiveClik = true;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), aPostiveArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();
            //it make rest of the button false and defult red backgroud text is white

            aNegativeClik = false;
            bPositiveClik = false;
            bNegativeClik = false;
            abPositiveClik = false;
            abNegativeClik = false;
            oPositiveClik =false;
            oNegativeClik = false;

            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);


            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));


        }else {
            aPositiveClik = false;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();



        }
    }
    public void aNegativeClicked(){
        if(!aNegativeClik){
            aNegativeClik = true;
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), aNegativeArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();


            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;

            bPositiveClik  = false;
            bNegativeClik  =  false;
            abPositiveClik = false;
            abNegativeClik = false;
            oPositiveClik  =  false;
            oNegativeClik  =  false;

            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));

            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));


        }else {
            aNegativeClik = false;
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();
        }
    }
    public void bPostiveClicked(){
        if(!bPositiveClik){
            bPositiveClik = true;
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), bPostiveArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();

            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;
            aNegativeClik = false;

            bNegativeClik = false;
            abPositiveClik = false;
            abNegativeClik = false;
            oPositiveClik =false;
            oNegativeClik = false;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));

            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));


        }else {
            bPositiveClik = false;
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();
        }
    }
    public void bNegativeClicked(){
        if(!bNegativeClik){
            bNegativeClik = true;
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), bNegativeArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();


            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;
            aNegativeClik = false;
            bPositiveClik = false;

            abPositiveClik = false;
            abNegativeClik = false;
            oPositiveClik =false;
            oNegativeClik = false;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));

            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));

        }else {
            bNegativeClik = false;
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();
        }
    }
    public void abPostiveClicked(){
        if(!abPositiveClik){
            abPositiveClik = true;
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), abPostiveArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();


            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;
            aNegativeClik = false;
            bPositiveClik = false;
            bNegativeClik = false;

            abNegativeClik = false;
            oPositiveClik =false;
            oNegativeClik = false;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));

            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));


        }else {
            abPositiveClik = false;
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();
        }
    }
    public void abNegativeClicked(){
        if(!abNegativeClik){
            abNegativeClik = true;
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();

            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;
            aNegativeClik = false;
            bPositiveClik = false;
            bNegativeClik = false;
            abPositiveClik = false;

            oPositiveClik =false;
            oNegativeClik = false;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));

            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));

        }else {
            abNegativeClik= false;
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();



        }
    }
    public void oPostiveClicked(){
        if(!oPositiveClik){
            oPositiveClik = true;
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), oPostiveArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();
            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;
            aNegativeClik = false;
            bPositiveClik = false;
            bNegativeClik = false;
            abPositiveClik = false;
            abNegativeClik = false;

            oNegativeClik = false;
            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));

            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
        }else {
            oPositiveClik = false;
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(), allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();


        }
    }
    public void oNegativeClicked(){
        if(!oNegativeClik){
            oNegativeClik = true;
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.white);
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#000000"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(),oNegativeArrayList);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();

            //it make rest of the button false and defult red backgroud
            aPositiveClik = false;
            aNegativeClik = false;
            bPositiveClik = false;
            bNegativeClik = false;
            abPositiveClik = false;
            abNegativeClik = false;
            oPositiveClik =false;

            fragmentBloodShowBinding.aPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.aNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.bNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.abNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oPostiveBtn.setBackgroundResource(R.color.bloodbgcolor);

            fragmentBloodShowBinding.aPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.aNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.bNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abPostiveBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.abNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            fragmentBloodShowBinding.oPostiveBtn.setTextColor(Color.parseColor("#ffffff"));


        }else {
            oNegativeClik = false;
            fragmentBloodShowBinding.oNegativeBtn.setBackgroundResource(R.color.bloodbgcolor);
            fragmentBloodShowBinding.oNegativeBtn.setTextColor(Color.parseColor("#ffffff"));
            bloodDonerAdapter = new BloodDonerAdapter(getContext(),allBloodTypeDoner);
            fragmentBloodShowBinding.showBloodRecylerViewFragment.setAdapter(bloodDonerAdapter);
            bloodDonerAdapter.notifyDataSetChanged();




        }
    }

    public void fetchApositiveDoner(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A+ Bood Group");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    aPostiveArrayList.add(bloodGroupModel);

                }
                allBloodTypeDoner.addAll(aPostiveArrayList);
                bloodDonerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });

    }
    public void fetchAnegativeDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A- Bood Group");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    aNegativeArrayList.add(bloodGroupModel);
                }
                allBloodTypeDoner.addAll(aNegativeArrayList);
                bloodDonerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });

    }
    public void fetchBpositiveDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B+ Bood Group");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {// Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bPostiveArrayList.add(bloodGroupModel);
                }
                allBloodTypeDoner.addAll(bPostiveArrayList);
                bloodDonerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }
    public void fetchBnegativeDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B- Bood Group").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bNegativeArrayList.add(bloodGroupModel);
                }
                allBloodTypeDoner.addAll(bNegativeArrayList);
                bloodDonerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }
    public void fetchABpositiveDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AB+ Bood Group");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    abPostiveArrayList.add(bloodGroupModel);
                }
                allBloodTypeDoner.addAll(abPostiveArrayList);
                bloodDonerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }
    public void fetchABnegativeDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AB- Bood Group");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    abegativeArrayList.add(bloodGroupModel);
                }

                allBloodTypeDoner.addAll(abegativeArrayList);
                bloodDonerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }
    public void fetchOpositiveDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O+ Bood Group");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    oPostiveArrayList.add(bloodGroupModel);
                }
                allBloodTypeDoner.addAll(oPostiveArrayList);
                bloodDonerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }
    public void fetchOnegativeDoner(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O- Bood Group");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    oNegativeArrayList.add(bloodGroupModel);
                }
                allBloodTypeDoner.addAll(oNegativeArrayList);
                bloodDonerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }


}