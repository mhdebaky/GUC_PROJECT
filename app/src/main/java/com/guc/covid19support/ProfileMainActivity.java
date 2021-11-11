package com.guc.covid19support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileMainActivity extends AppCompatActivity {
    SignInButtonImpl btnMaps;
    SignInButtonImpl btnLogOut;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    SignInButtonImpl profileBtn;
    TextView textViewToShMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        setSupportActionBar(findViewById(R.id.profile_main_activity_toolbar));
        getSupportActionBar().setTitle("User Main Page");
        String mail = getIntent().getStringExtra("email");
        textViewToShMail = findViewById(R.id.email_textview_Laytout);
        textViewToShMail.setText(mail);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        btnLogOut=findViewById(R.id.LogoutBtn);
        profileBtn = findViewById(R.id.MainProfileBtn);
        btnMaps = findViewById(R.id.MapsBtn);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        /****************************profile user name and age update**********************************/
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMainActivity.this,ProfileActivity.class);
                startActivity(intent);

            }
        });
        /****************************profile user name and age update**********************************/
        /****************************Logout Button**********************************/
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(ProfileMainActivity.this,"Loged out",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileMainActivity.this,SignIn_SingUp_Activity.class);
                startActivity(intent);
                ProfileMainActivity.this.finish();

            }
        });
        /****************************Logout Button**********************************/

    }
}