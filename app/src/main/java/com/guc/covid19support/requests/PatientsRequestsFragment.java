package com.guc.covid19support.requests;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guc.covid19support.R;
import com.guc.covid19support.adapters.RecViewAdapter;

import java.util.ArrayList;


public class PatientsRequestsFragment extends Fragment {


    View view;
    RecViewAdapter recViewAdapter;
    RecyclerView recyclerView;
    ArrayList<Request>  requests;
    FirebaseUser user;
    DatabaseReference reqUsersRef,requestsRef,usersRef;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requests = new ArrayList<>();
        user= FirebaseAuth.getInstance().getCurrentUser();
        reqUsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Requests");
        requestsRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        ArrayList<String> reqIds=new ArrayList<>();
        recViewAdapter = new RecViewAdapter(getContext(),requests,requestsRef,true);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        reqUsersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    ids.add(dataSnapshot.getValue().toString());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });

        requestsRef.addValueEventListener(new ValueEventListener() {

            Request request ;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
                    request = dataSnapshot.getValue(Request.class);
                    if(!reqIds.contains(request.getReqId())){
                        reqIds.add(request.getReqId());
                        if(request.getReply().toLowerCase().equals("no reply yet")){
                            requests.add(request);
                        }
                    }
                    recViewAdapter.notifyDataSetChanged();


                }
                recViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_patients_requests, container, false);
        recyclerView = view.findViewById(R.id.doctor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(recViewAdapter);

        return view;
    }


}