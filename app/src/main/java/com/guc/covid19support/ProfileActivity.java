package com.guc.covid19support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class ProfileActivity extends AppCompatActivity {
    TextInputEditText mName;
    TextInputEditText mAge;
    AppCompatButton updateBtn;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference firebasedb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSupportActionBar(findViewById(R.id.profile_activity_toolbar));
        mName = findViewById(R.id.name_editText_profile_activity);
        mAge = findViewById(R.id.age_editText_profile_activity);
        updateBtn = findViewById(R.id.update_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebasedb = FirebaseDatabase.getInstance().getReference().child("Users");
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAge.getText().toString().equals("")||mName.getText().toString().equals("")){
                    Toast.makeText(ProfileActivity.this,"please fill both name and age ",Toast.LENGTH_LONG).show();
                    mName.setError("Please type your name");
                    mAge.setError("Please type your age");
                    mName.requestFocus();
                    mAge.requestFocus();
                }else{

                    firebasedb.child(user.getUid()).child("Name").setValue(mName.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ProfileActivity.this,"Name Updated Successfuly",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    firebasedb.child(user.getUid()).child("Age").setValue(Integer.parseInt(mAge.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ProfileActivity.this,"Age Updated Successfuly",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    firebasedb.child(user.getUid()).child("Age").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue() != null){
                                String message = "Age : " + snapshot.getValue();
                                Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_LONG).show();
                            }
                            if (snapshot.getValue() != null) {

                                    Log.d("data not null", "" + snapshot.getValue()); // your name values you will get here

                            } else {
                                Log.e("data null", " it's null.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}