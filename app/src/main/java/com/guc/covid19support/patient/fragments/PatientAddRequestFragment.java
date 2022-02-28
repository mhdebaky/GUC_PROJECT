package com.guc.covid19support.patient.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guc.covid19support.R;
import com.guc.covid19support.requests.Request;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


public class PatientAddRequestFragment extends Fragment {
    //views declaration
    MaterialButton submitRequestBtn;
    TextInputEditText requestText;
    ///////////////////////////////////////

    //database declerations
    FirebaseUser user;
    DatabaseReference refUsers,refRequests;
    ///////////////////////////////////////
    Date date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_request, container, false);

        submitRequestBtn = view.findViewById(R.id.patient_add_request_button);
        requestText = view.findViewById(R.id.patient_request_input_text);

        user = FirebaseAuth.getInstance().getCurrentUser();
        refUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        refRequests = FirebaseDatabase.getInstance().getReference().child("Requests");
        ArrayList<String> userData = new ArrayList<>();
        Request request = new Request();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("EGY"));
        date = calendar.getTime();
        ContentLoadingProgressBar progressBar = view.findViewById(R.id.patient_add_request_progressbar);
        refUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(dataSnapshot.getKey().toString().equals("name")){
                        request.setUserName(dataSnapshot.getValue().toString());
                    }else if(dataSnapshot.getKey().toString().equals("age")){
                        request.setUserAge(dataSnapshot.getValue().toString());
                    }
                    else if(dataSnapshot.getKey().toString().equals("phone")){
                        request.setUserPhone(dataSnapshot.getValue().toString());
                    }
                }
                progressBar.setVisibility(View.GONE);
                FrameLayout layout = view.findViewById(R.id.patient_request_add_framelayout);
                layout.removeView(view.findViewById(R.id.blur_linearlayout));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        submitRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestTextString="";
                if(!requestText.getText().toString().equals("")){
                    requestTextString = requestText.getText().toString();
                }else{
                    requestText.setError("Please Enter Your Symptoms");
                    requestText.requestFocus();
                }
                String requestId = UUID.randomUUID().toString();
                request.setReqId(requestId);
                request.setSymptoms(requestTextString);
                request.setTimeStamp(date.toString());
                request.setReply("No reply yet");
                if(!request.getSymptoms().equals("")){
                    refUsers.child(user.getUid()).child("Requests").child(requestId).setValue(requestId);
                    refRequests.child(requestId).setValue(request);
                }


                Toast.makeText(getActivity(), date.toString() +"  " +"Text:" + request.getSymptoms()+" "+request.getReply(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
        
    }

}