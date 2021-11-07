package com.guc.covid19support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignIn_SingUp_Activity extends AppCompatActivity {

    TextInputEditText mUserEmail;
    TextInputEditText mPassword;
    SignInButtonImpl signInButton;
    SignInButtonImpl singup_button;
    ProgressBar progressBar ;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signin_signup_activity);
        Animation anime = AnimationUtils.loadAnimation(this,R.anim.fade);
        anime.reset();
        anime.setDuration(1600);
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
                Intent intent = new Intent(SignIn_SingUp_Activity.this,Patient_Or_Doctor_Activity.class);
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
                                Toast.makeText(SignIn_SingUp_Activity.this,"Logged in Successfuly",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignIn_SingUp_Activity.this,ProfileMainActivity.class);
                                startActivity(intent);
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


}
