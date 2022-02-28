package com.guc.covid19support.registeration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guc.covid19support.profilemainactivity.DoctorProfileMainActivity;
import com.guc.covid19support.R;
import com.guc.covid19support.profilemainactivity.PatientProfileMainActivity;


public class SignIn_SingUp_Activity extends AppCompatActivity {

    TextInputEditText mUserEmail;
    TextInputEditText mPassword;
    AppCompatButton signInButton;
    AppCompatButton singup_button;
    ProgressBar progressBar ;
    FirebaseAuth firebaseAuth;
    Boolean isPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signin_signup_activity);
        Animation anime = AnimationUtils.loadAnimation(this,R.anim.fade);
        anime.reset();
        anime.setDuration(2400);
        TextView v2 = findViewById(R.id.sign_in_v2);
        v2.clearAnimation();
        v2.startAnimation(anime);
        signInButton = findViewById(R.id.singin_button);
        singup_button = findViewById(R.id.signup_btn);
        mUserEmail = findViewById(R.id.editText_username);
        mPassword=findViewById(R.id.editText_password);
        progressBar=findViewById(R.id.singin_singup_progressbar);
        firebaseAuth = FirebaseAuth.getInstance();
        singup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_SingUp_Activity.this, Patient_Or_Doctor_Activity.class);
                startActivity(intent);

            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(mUserEmail.getText().toString().equals("") || mPassword.getText().toString().equals("")){
                    if(mUserEmail.getText().toString().equals("")){
                        mUserEmail.setError("Please Enter your email");
                        mUserEmail.requestFocus();

                    }
                    if(mPassword.getText().toString().equals("")){
                        mPassword.setError("Please Enter password");
                        mUserEmail.requestFocus();
                    }
                    Toast.makeText(SignIn_SingUp_Activity.this,"Please enter your email and password correctly",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    firebaseAuth.signInWithEmailAndPassword(mUserEmail.getText().toString(),mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                toIntent();

                            }else{
                                Log.d("authentication", "onComplete: "+task.getException().getMessage().toString());
                                Toast.makeText(SignIn_SingUp_Activity.this,task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });



    }
    public void toIntent(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        this.isPatient = false;
        userRef.child("isPatient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isPatient = (Boolean) snapshot.getValue();
                if(isPatient){
                    Intent intent = new Intent(SignIn_SingUp_Activity.this, PatientProfileMainActivity.class);
                    intent.putExtra("isPatient", true);
                    intent.putExtra("isDoctor", false);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SignIn_SingUp_Activity.this, DoctorProfileMainActivity.class);
                    intent.putExtra("isPatient", false);
                    intent.putExtra("isDoctor", true);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
