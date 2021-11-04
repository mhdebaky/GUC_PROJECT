package com.guc.covid19support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Patient_SignUP_Activity extends AppCompatActivity {
    TextInputEditText mEmail;
    TextInputEditText mPass;
    TextInputEditText mReEntrPass;
    SignInButtonImpl mSignUpBtn;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseUser user;
    DatabaseReference firebasedb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
        setSupportActionBar((androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar_patient_signup));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEmail = findViewById(R.id.email_patient_signUp_activity);
        mPass = findViewById(R.id.password_patient_signUp_activity);
        mReEntrPass = findViewById(R.id.Re_enter_Password_patient_signUp_activity);
        mSignUpBtn = findViewById(R.id.signup_btn_patient_signUp_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebasedb = FirebaseDatabase.getInstance().getReference();
        progressBar = findViewById(R.id.progressBar_patient_signUp_activity);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString();
                String pass = mPass.getText().toString();
                String reEnteredPass = mReEntrPass.getText().toString();
                if (email.equals("") || pass.equals("") || reEnteredPass.equals("")) {
                    if (email.equals("")) {
                        mEmail.setError("Please Enter an email address");
                        mEmail.requestFocus();
                    }
                    if (pass.equals("")) {
                        mPass.setError("Please Enter password");
                        mPass.requestFocus();
                    }
                    if (reEnteredPass.equals("")) {
                        mReEntrPass.setError("Please Re-Enter password");
                        mReEntrPass.requestFocus();
                    }
                    progressBar.setVisibility(View.GONE);
                } else{
                    if (!pass.equals(reEnteredPass)) {
                        Toast.makeText(Patient_SignUP_Activity.this, "Please make sure the password fields are matching", Toast.LENGTH_LONG).show();
                        mReEntrPass.setError("Does not match the password");
                        mReEntrPass.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    }else{
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Toast.makeText(Patient_SignUP_Activity.this, "Successfuly Registered", Toast.LENGTH_LONG).show();
                                    firebaseAuth.signInWithEmailAndPassword(email,pass);
                                    user = firebaseAuth.getCurrentUser();
                                    firebasedb.child("Users").child("Patients").child(user.getUid()).child("email").setValue(user.getEmail());
                                    Intent intent = new Intent(Patient_SignUP_Activity.this,ProfileMainActivity.class);
                                    startActivity(intent);
                                    Patient_SignUP_Activity.this.finish();
                                }else{
                                    Toast.makeText(Patient_SignUP_Activity.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                }
            }
        });


    }
}