package com.guc.covid19support.registeration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guc.covid19support.UserData;
import com.guc.covid19support.profilemainactivity.DoctorProfileMainActivity;
import com.guc.covid19support.profilemainactivity.PatientProfileMainActivity;
import com.guc.covid19support.R;

public class Doctor_SignUp_Activity extends AppCompatActivity {
    TextInputEditText mName;
    TextInputEditText mAge;
    TextInputEditText mPhone;
    TextInputEditText mEmail;
    TextInputEditText mPass;
    TextInputEditText mReEntrPass;
    MaterialButton mSignUpBtn;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseUser user;
    DatabaseReference firebasedb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);
        setSupportActionBar((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_doctor_signup));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mName = findViewById(R.id.name_editText_profile_activity_doctor);
        mAge = findViewById(R.id.age_editText_profile_activity_doctor);
        mPhone = findViewById(R.id.phone_editText_profile_activity_doctor);
        mEmail = findViewById(R.id.email_doctor_signUp_activity);
        mPass = findViewById(R.id.password_doctor_signUp_activity);
        mReEntrPass = findViewById(R.id.Re_enter_Password_doctor_signUp_activity);
        mSignUpBtn = findViewById(R.id.signup_btn_doctor_signUp_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebasedb = FirebaseDatabase.getInstance().getReference();
        progressBar = findViewById(R.id.progressBar_doctor_signUp_activity);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name = mName.getText().toString();
                String age = mAge.getText().toString();
                String phone = mPhone.getText().toString();
                String email = mEmail.getText().toString();
                String pass = mPass.getText().toString();
                String reEnteredPass = mReEntrPass.getText().toString();
                Boolean makeAccountRequestOk = isTextFieldsAllFilled(name,age,phone,email,pass,reEnteredPass);
                if(makeAccountRequestOk){
                    UserData userData = new UserData(name,age,phone,email,false);
                    firebaseAuth.createUserWithEmailAndPassword(email,pass);
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            firebasedb.child("Users").child(user.getUid()).setValue(userData);
                            Toast.makeText(Doctor_SignUp_Activity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            toIntent(user);
                        }
                    });

                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Doctor_SignUp_Activity.this, "Please Register Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void toIntent(FirebaseUser user){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        Boolean isPatient = userRef.child("isPatient").equals(true);
        Intent intent = new Intent(Doctor_SignUp_Activity.this, DoctorProfileMainActivity.class);
        intent.putExtra("isPatient", false);
        intent.putExtra("isDoctor", true);
        startActivity(intent);
        this.finish();

    }
    public Boolean isTextFieldsAllFilled(String name,String age,String phone,String mail,String pass,String reEnteredPass){
        boolean f;
        if(name.equals("") || age.equals("") || phone.equals("") || mail.equals("") || pass.equals("") || reEnteredPass.equals("")  ){

            if (name.equals("")) {
                mEmail.setError("Please Enter an name address");
                mEmail.requestFocus();
            }

            if (age.equals("")) {
                mEmail.setError("Please Enter an age address");
                mEmail.requestFocus();
            }

            if (phone.equals("")) {
                mEmail.setError("Please Enter an phone address");
                mEmail.requestFocus();
            }
            if (mail.equals("")) {
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
            if(reEnteredPass.equals(reEnteredPass)){
                mReEntrPass.setError("Please Re-Enter password correctly");
                mReEntrPass.requestFocus();
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
            f = false;

        }else{
            f =  true;
        }

        return f;
    }
}