package com.example.anowarahelpline.blood;

import static android.content.Context.MODE_PRIVATE;

import static com.example.anowarahelpline.blood.SelectBloodActivity.imageRef;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anowarahelpline.MainActivity;
import com.example.anowarahelpline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class BloodDonerProfile extends Fragment {

    public static String bloodGroup ="";
    FirebaseAuth mAuth;
    String currentUser;
    TextView txtName,txtBloodGroup,txtMobileNumber,txtUpozila,txtEmail,deleteBtn;
    String setName,setBloodGroup,setMobileNumber,setUpozila,setEmail,setImage,setBlooDOnateDate;
    CircleImageView imageView;

    String imgUrl,email;

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
        deleteBtn = view.findViewById(R.id.blodDonerprofileDeleteBtn);

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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showalartBeforeDelete();
            }
        });
        return view;
    }

    private void showalartBeforeDelete() {
        final EditText passwordEditText = new EditText(getContext());
        passwordEditText.setHint("Enter your password");

        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Password")
                .setMessage("Please enter your password to delete your account.")
                .setView(passwordEditText)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = passwordEditText.getText().toString().trim();
                        if (!password.isEmpty()) {
                            reAuthenticateAndDelete(password); // Call the re-authentication method
                        } else {
                            Toast.makeText(getContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }

    private void reAuthenticateAndDelete(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {// Get the email address
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            // Re-authenticate
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Proceed with deletion if re-authentication succeeds
                        deleteUserAccount();
                    } else {
                        // Handle re-authentication failure
                        Toast.makeText(getContext(), "password is worng.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void deleteUserAccount() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Step 1: Delete user data from Firebase Authentication
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Get a reference to the SharedPreferences object
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("BloodSharedPref", MODE_PRIVATE);
                        // Get an editor to write to the SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("is already shows", false);
                        // Apply the changes
                        editor.apply();
                        // Step 5: Sign out and redirect to login screen
                        mAuth.signOut();

                        deleteImageFromStorage(imgUrl);
                        //databse refrence
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(bloodGroup).child(currentUser);
                        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });

                    } else {
                        if (task.getException() != null) {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                            Log.d("delete",error);
                        }
                    }
                }
            });
        }
    }

    private void deleteImageFromStorage(String imgUrl) {
        // Step 3: Get the storage reference from the image URL
        StorageReference storageReference = imageRef;

        // Step 3: Delete the image from Firebase Storage
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Image deleted successfully
                Log.d("delete", "Image deleted successfully.");

                // Proceed with deleting user data from Firebase Authentication and Database

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to delete the image
                Log.d("delete", "Failed to delete image: " + e.getMessage());
                Toast.makeText(getContext(), "Failed to delete image: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                // Proceed with deleting user data anyway (if desired)

            }
        });
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
                if (snapshot.exists()){

                        BloodGroupModel bloodGroupModel = snapshot.getValue(BloodGroupModel.class);

                        String name = bloodGroupModel.getName();
                        String bloodGroup = bloodGroupModel.getBloodGroup();
                        email = bloodGroupModel.getEmail();
                        imgUrl = bloodGroupModel.getDownloadImgUri();
                        String mobileNumber = bloodGroupModel.getMobileNumber();

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