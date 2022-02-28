package com.guc.covid19support.patient.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guc.covid19support.R;
import com.guc.covid19support.UserData;
import com.guc.covid19support.profilemainactivity.PatientProfileMainActivity;
import com.guc.covid19support.voip.udpcall.MainCallActivity;

import java.util.ArrayList;


public class PatientProfileFragment extends Fragment {
    //initallization of textinputtextfields
    TextInputEditText name;
    TextInputEditText age;
    TextInputEditText phone;
    TextView userType;
    TextInputEditText email;
    //////////////////////////////////////////////

    //intialization of Edit btn
    MaterialButton editBtn;
    MaterialButton callActivityBtn;
    /////////////////////////////////

    //Database refrece and user refrence
    DatabaseReference usersRef;
    FirebaseUser user;
    ///////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initallization of textinputtextfields
        View view =inflater.inflate(R.layout.fragment_profile_patient, container, false);
        name = view.findViewById(R.id.patient_profile_edit_text_name_of_patient);
        age = view.findViewById(R.id.patient_profile_edit_text_age);
        phone = view.findViewById(R.id.patient_profile_edit_text_phone);
        userType = view.findViewById(R.id.patient_profile_edit_text_user_type);
        email = view.findViewById(R.id.patient_profile_edit_text_mail);
        changeStateOfTextInputEditText(name,age,phone,email,false);
        /////////////////////////////////////////////////////
        //Edit inintialization
        editBtn = view.findViewById(R.id.patient_profile_button_to_edit);
        callActivityBtn = view.findViewById(R.id.patient_profile_call);
        //////////////////////
        //database and user
        user = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        //////////////////////////////////////////////////////


        //retrieving user data from the database
        final UserData[] userData = {null};
        usersRef.addValueEventListener(new ValueEventListener() {
            UserData data;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().toString().equals(user.getUid().toString())){
                        userData[0] = dataSnapshot.getValue(UserData.class);
                        changeEditTextValues(userData);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /////////////////////////////////////////////////////////

        //edit button function
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStateOfTextInputEditText(name,age,phone,email,editBtn.getText().toString(),userData);
            }
        });
        callActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainCallActivity.class);
                intent.putExtra("Name",userData[0].getName());
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////



        return view;
    }

    public void changeEditTextValues(UserData[] userData){
        UserData user = userData[0];
        name.setText(user.getName());
        age.setText(user.getAge());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        if(user.getisPatient()){
            userType.setText("Patient");
        }else{
            userType.setText("Doctor");
        }
    }
    public void editDataAfterSubmitBtn(UserData userData){

        String nameFromDataBase = userData.getName();
        String ageFromDataBase = userData.getAge();
        String phoneFromDataBase = userData.getPhone();
        String emailromDataBase = userData.getEmail();
        ArrayList<String> dataFromDatabase = new
                ArrayList<>(4);
        dataFromDatabase.add(nameFromDataBase);
        dataFromDatabase.add(ageFromDataBase);
        dataFromDatabase.add(phoneFromDataBase);
        dataFromDatabase.add(emailromDataBase);

        ArrayList<String> dataFromLocalAccount = new ArrayList<>(4);
        dataFromLocalAccount.add(name.getText().toString());
        dataFromLocalAccount.add(age.getText().toString());
        dataFromLocalAccount.add(phone.getText().toString());
        dataFromLocalAccount.add(email.getText().toString());
        if(!dataFromDatabase.get(0).equals(dataFromLocalAccount.get(0))){
            usersRef.child(user.getUid()).child("name").setValue(dataFromLocalAccount.get(0));
        }if(!dataFromDatabase.get(1).equals(dataFromLocalAccount.get(1))){
            usersRef.child(user.getUid()).child("age").setValue(dataFromLocalAccount.get(1));
        }if(!dataFromDatabase.get(2).equals(dataFromLocalAccount.get(2))){
            usersRef.child(user.getUid()).child("phone").setValue(dataFromLocalAccount.get(2));
        }if(!dataFromDatabase.get(3).equals(dataFromLocalAccount.get(3))){
            user.updateEmail(dataFromLocalAccount.get(3));
            usersRef.child(user.getUid()).child("email").setValue(dataFromLocalAccount.get(3));
        }
    }


    public void changeStateOfTextInputEditText(TextInputEditText name, TextInputEditText age, TextInputEditText phone, TextInputEditText email,String buttonText, UserData[] userData) {
        if(buttonText.toLowerCase().equals("edit")){
            helperChangeStatToBoolean(name,true);
            helperChangeStatToBoolean(age,true);
            helperChangeStatToBoolean(phone,true);
            helperChangeStatToBoolean(email,true);
            editBtn.setText("submit");
        }else if(buttonText.toLowerCase().equals("submit")){
            editBtn.setText("edit");
            helperChangeStatToBoolean(name,false);
            helperChangeStatToBoolean(age,false);
            helperChangeStatToBoolean(phone,false);
            helperChangeStatToBoolean(email,false);
            editDataAfterSubmitBtn(userData[0]);
        }
    }
    public void changeStateOfTextInputEditText(TextInputEditText name, TextInputEditText age, TextInputEditText phone, TextInputEditText email,boolean flag){

            helperChangeStatToBoolean(name,flag);
            helperChangeStatToBoolean(age,flag);
            helperChangeStatToBoolean(phone,flag);
            helperChangeStatToBoolean(email,flag);

    }
    public void helperChangeStatToBoolean(TextInputEditText textInputEditText,boolean flag){
        textInputEditText.setClickable(flag);
        textInputEditText.setCursorVisible(flag);
        textInputEditText.setFocusableInTouchMode(flag);

    }
}