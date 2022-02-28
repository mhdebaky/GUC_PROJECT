package com.guc.covid19support.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.guc.covid19support.R;

public class Patient_Or_Doctor_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_or_doctor);
        androidx.appcompat.widget.Toolbar appBarLayout = findViewById(R.id.actionbar1);
        setSupportActionBar(appBarLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button doctorSignUpBtn = findViewById(R.id.SignUp_Doctor);
        Button patientSignUpBtn = findViewById(R.id.SignUp_patient);
        doctorSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Or_Doctor_Activity.this, Doctor_SignUp_Activity.class);
                startActivity(intent);
            }
        });
        patientSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Or_Doctor_Activity.this, Patient_SignUP_Activity.class);
                startActivity(intent);
            }
        });



    }
}