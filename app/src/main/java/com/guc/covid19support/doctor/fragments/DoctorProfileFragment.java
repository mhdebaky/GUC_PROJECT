package com.guc.covid19support.doctor.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.guc.covid19support.voip.udpcall.MainCallActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.ArrayList;


public class DoctorProfileFragment extends Fragment {
    //initallization of textinputtextfields
    TextInputEditText name;
    TextInputEditText age;
    TextInputEditText phone;
    TextView userType;
    TextInputEditText email;
    //////////////////////////////////////////////
    //initaialize image btn
    ImageButton editImage;
    CircularImageView imageView;
    ////////////////////////
    //intialization of Edit btn
    MaterialButton editBtn;
    MaterialButton callBtn;
    /////////////////////////////////

    //Database refrece and user refrence
    DatabaseReference usersRef;
    FirebaseUser user;
    ///////////////////////////////////////

    final int ACTION_REQUEST_GALLERY = 1;
    final int ACTION_REQUEST_CAMERA = 2;
    Uri filePath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initallization of textinputtextfields
        View view =inflater.inflate(R.layout.fragment_doctor_profile, container, false);
        name = view.findViewById(R.id.doctor_profile_edit_text_name_of_doctor);
        age = view.findViewById(R.id.doctor_profile_edit_text_age);
        phone = view.findViewById(R.id.doctor_profile_edit_text_phone);
        userType = view.findViewById(R.id.doctor_profile_edit_text_user_type);
        email = view.findViewById(R.id.doctor_profile_edit_text_mail);
        changeStateOfTextInputEditText(name,age,phone,email,false);
        /////////////////////////////////////////////////////
        //Edit inintialization
        editBtn = view.findViewById(R.id.doctor_profile_button_to_edit);
        callBtn = view.findViewById(R.id.doctor_profile_button_to_call);
        //////////////////////
        //database and user
        user = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        //////////////////////////////////////////////////////

        //components//////
        editImage = view.findViewById(R.id.doctor_edit_image_pic);
        imageView = view.findViewById(R.id.doctor_image_view);
        /////////////////


        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiloag();
            }
        });

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
        callBtn.setOnClickListener(new View.OnClickListener() {
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
            usersRef.child(user.getUid()).child("email").setValue(dataFromLocalAccount.get(3));
            user.updateEmail(dataFromLocalAccount.get(3));
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
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        //startActivityForResult(Intent.createChooser(intent, "Select Picture......"), PICK_IMAGE_REQUEST);
    }
    // Override onActivityResult method
    public void showDiloag(){
        Dialog dialog = new Dialog(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[] { "Gallery", "Camera" },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");

                                Intent chooser = Intent
                                        .createChooser(
                                                intent,
                                                "Choose a Picture");
                                startActivityForResult(
                                        chooser,
                                        ACTION_REQUEST_GALLERY);

                                break;

                            case 1:
                                Intent cameraIntent = new Intent(
                                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(
                                        cameraIntent,
                                        ACTION_REQUEST_CAMERA);

                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();
        dialog.dismiss();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == ACTION_REQUEST_GALLERY) {
            // Get the url of the image from data
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == ACTION_REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras()
                    .get("data");
            imageView.setImageBitmap(photo);
        }
    }
}