package com.example.anowarahelpline.blood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class BloodGroupAnegative extends Fragment {
    String bloodGroup;

    RecyclerView recyclerView;
    BloodDonerAdapter bloodDonerAdapter;
    String currentUser;

    SearchView searchView ;
    ArrayList<BloodGroupModel> bLoodDonerArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_group_anegative, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        searchView = view.findViewById(R.id.bloodSearch);
        recyclerView = view.findViewById(R.id.showBloodRecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            bloodGroup = bundle.getString("bloodGroup");
            if (bloodGroup.equals("A+")) {
                fetchApositive();
                Log.d("bloodGroup", bloodGroup);
            } else if (bloodGroup.equals("A-")) {
                fetchAnegative();
            } else if (bloodGroup.equals("B+")) {
                fetcBpositive();
            } else if (bloodGroup.equals("B-")) {
                fetchBegative();
            } else if (bloodGroup.equals("AB+")) {
                fetchABpositive();
            } else if (bloodGroup.equals("AB-")) {
                fetchABnegative();
            } else if (bloodGroup.equals("O+")) {
                fetchOpositive();
            } else if (bloodGroup.equals("O-")) {
                fetchOnegative();
            }
        }


        //work on searchView
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searhText(newText);
                return true;
            }
        });

        return view;
    }

    private void searhText(String newText) {
        ArrayList<BloodGroupModel> dataSearchList = new ArrayList<>();
        for (BloodGroupModel data : bLoodDonerArrayList){
            if (data.getName().toLowerCase().contains(newText.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            bloodDonerAdapter.setSearchList(dataSearchList);
        }
    }

    private void fetchOnegative() {



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O- Bood Group").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }

    private void fetchOpositive() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O+ Bood Group").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });

    }

    private void fetchABnegative() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AB- Bood Group").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }

    private void fetchABpositive() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AB+ Bood Group").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }

    private void fetchBegative() {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B- Bood Group").child(currentUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }

    private void fetcBpositive() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("B+ Bood Group");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }

    private void fetchAnegative() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A- Bood Group");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }

    private void fetchApositive() {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("A+ Bood Group");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bLoodDonerArrayList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodGroupModel bloodGroupModel = dataSnapshot.getValue(BloodGroupModel.class);
                    bLoodDonerArrayList.add(bloodGroupModel);
                }
                // Notify your adapter that data has changed
                bloodDonerAdapter = new BloodDonerAdapter(getContext(), bLoodDonerArrayList);
                recyclerView.setAdapter(bloodDonerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("fetchApositive", "Error fetching data: " + error.getMessage());
            }
        });
    }
}