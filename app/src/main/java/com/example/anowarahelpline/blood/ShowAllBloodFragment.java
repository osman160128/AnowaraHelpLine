package com.example.anowarahelpline.blood;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.FragmentShowAllBloodBinding;

public class ShowAllBloodFragment extends Fragment {


    private FragmentShowAllBloodBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_all_blood, container, false);

        binding.setShowallbloodfragment(this);

        // Return the root view
        return binding.getRoot();
    }


    public void getApositive(){

        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "A+");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public void getAnegative(){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "B+");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void getBpositvie(){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "B+");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void getBnegative(){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "B-");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void getABPositvie(){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "AB+");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void getABnegative(){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "AB-");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void getOposetive(){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "O+");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void getOnegative( ){
        BloodGroupAnegative bloodGroupAnegative = new BloodGroupAnegative();
        Bundle bundle = new Bundle();
        bundle.putString("bloodGroup", "O-");
        bloodGroupAnegative.setArguments(bundle);

        // Replace FragmentA with FragmentB
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bloodDonerFrameLyout, bloodGroupAnegative);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}