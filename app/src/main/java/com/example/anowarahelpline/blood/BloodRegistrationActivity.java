package com.example.anowarahelpline.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anowarahelpline.R;
import com.example.anowarahelpline.databinding.ActivityBloodLoginBinding;
import com.example.anowarahelpline.databinding.ActivityBloodRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BloodRegistrationActivity extends AppCompatActivity {


    ActivityBloodRegistrationBinding activityBloodRegistrationBinding;
    String uploadId;
    String finalMobileNumber;

    TextInputEditText edtDonarName,edtDonarMobileNumber,
            edtDonarEmail, edtDonarPassword, edtDonarPossordConform;

    CardView bloodGroupItem;

    // blood group  button


    TextView continueBtn;
    private FirebaseAuth mAuth;

    ImageView bloodSignUpImage;

    TextView SignUpBtn;

    private Uri imageUri;

    private static final int IMAGE_REQUEST = 1;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    StorageTask uploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_registration);

        activityBloodRegistrationBinding = DataBindingUtil.setContentView(this,R.layout.activity_blood_registration);
        activityBloodRegistrationBinding.setBloodRegistear(this);

        //findView for all edittext an textView;
        edtDonarName = findViewById(R.id.bloodSignUpName);
        edtDonarMobileNumber = findViewById(R.id.bloodSignUpMobileNumber);
        edtDonarEmail = findViewById(R.id.bloodSignUpEmail);
        edtDonarPassword = findViewById(R.id.bloodSIgnUpPassword);
        edtDonarPossordConform = findViewById(R.id.bloodSIgnUpPasswordConform);
        bloodSignUpImage = findViewById(R.id.bloodSignUpImage);
        SignUpBtn = findViewById(R.id.signUpButton);


        //contunie btn

        continueBtn = findViewById(R.id.signUpContinueButton);



        //firebase reference
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("BloodDonerDetails");
        storageReference = FirebaseStorage.getInstance().getReference("BloodDonerDetails");



        bloodSignUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //-----start  select Image   ------
    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();
            Picasso.get()
                    .load(imageUri)
                    .into(bloodSignUpImage);

        }
    }

    //===============================
    public void continueBtn(){

        Toast.makeText(this, "continue click", Toast.LENGTH_SHORT).show();

        String name = edtDonarName.getText().toString().trim();
        String mobileNumber = edtDonarMobileNumber.getText().toString().trim();
        String email = edtDonarEmail.getText().toString().trim();
        String password = edtDonarPassword.getText().toString().trim();
        String conformPassword = edtDonarPossordConform.getText().toString().trim();

        if(!mobileNumber.startsWith("+88")){
            finalMobileNumber = "+88" + mobileNumber;
        }
        else {
            finalMobileNumber = mobileNumber;
        }

        String mobileRegex = "[+][8][8][0][1,3][6-9]";
        Matcher mobileMatcher;
        Pattern mobilePatter = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePatter.matcher(finalMobileNumber);

        //if any box is empty show erro masage start---
        if (imageUri==null) {
            Toast.makeText(this, "Please select a image ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(name)) {
            edtDonarName.setError("Please, enter user name");
            edtDonarName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(finalMobileNumber)) {
            edtDonarMobileNumber.setError("enter the image name");
            edtDonarMobileNumber.requestFocus();
            return;
        }
        else if(!mobileMatcher.find()){
            edtDonarMobileNumber.setError("Valid Mobile number is Required");
            edtDonarMobileNumber.requestFocus();
            return;
        }

        else if(finalMobileNumber.length()!=14){
            edtDonarMobileNumber.setError("Valid number is Required");
            edtDonarMobileNumber.requestFocus();
            return;
        }


        else if (TextUtils.isEmpty(email)) {
            edtDonarEmail.setError("enter the image name");
            edtDonarEmail.requestFocus();
            return;
        }


        else if (password.isEmpty() || password.length()<6) {
            edtDonarPassword.setError("Enter valid password");
            edtDonarPassword.requestFocus();
            return;
        }
        else if (conformPassword.isEmpty()) {
            edtDonarPassword.setError("Enter the conform passwor");
            edtDonarPassword.requestFocus();
            return;
        }
        else if(!password.equals(conformPassword)){

            edtDonarPossordConform.setError("Password doesn't match");
            edtDonarPossordConform.requestFocus();
            edtDonarPassword.clearComposingText();
            edtDonarPossordConform.clearComposingText();
        }

        else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //first i upload the image to firebase than chek blood group

                        Intent intent = new Intent(BloodRegistrationActivity.this,SelectBloodActivity.class);

                        intent.putExtra("name",name);
                        intent.putExtra("mobileNumber",mobileNumber );
                        intent.putExtra("password",password);
                        intent.putExtra("email",email);
                        intent.putExtra("imgUri",imageUri.toString());
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(BloodRegistrationActivity.this, "not succes", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}