package com.guc.covid19support;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    TextInputEditText mName;
    TextInputEditText mAge;
    SignInButtonImpl updateBtn;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference firebasedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mName = findViewById(R.id.name_editText_profile_activity);
        mAge = findViewById(R.id.age_editText_profile_activity);
        updateBtn = findViewById(R.id.update_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebasedb = FirebaseDatabase.getInstance().getReference();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAge.getText().toString().equals("")||mName.getText().toString().equals("")){
                    Toast.makeText(ProfileActivity.this,"please fill both name and age ",Toast.LENGTH_LONG).show();

                }else{

                }
            }
        });
    }
}