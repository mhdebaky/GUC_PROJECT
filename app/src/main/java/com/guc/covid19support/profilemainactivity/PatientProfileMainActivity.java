package com.guc.covid19support.profilemainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.guc.covid19support.R;
import com.guc.covid19support.adapters.PatientFragmentAdapter;
import com.guc.covid19support.registeration.SignIn_SingUp_Activity;

public class PatientProfileMainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    BottomNavigationView bottomNavigationView;
    ViewPager2 fragmentContainer;
    PatientFragmentAdapter adapter;
    public final static String EXTRA_CONTACT = "CONTACT";
    public final static String EXTRA_IP = "IP";
    public final static String EXTRA_DISPLAYNAME = "DISPLAYNAME";
    public final static String EXTRA_USER = "PATIENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main_patient);
        Toast.makeText(PatientProfileMainActivity.this,"Logged in Successfuly",Toast.LENGTH_LONG).show();
        bottomNavigationView = findViewById(R.id.bottom_navigation_patient);
        fragmentContainer = findViewById(R.id.fragment_container_patient);
        adapter = new PatientFragmentAdapter(this);
        fragmentContainer.setAdapter(adapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(R.id.profile_item == id){
                    fragmentContainer.setCurrentItem(0);
                }else if(R.id.show_requests_item == id){
                    fragmentContainer.setCurrentItem(1);
                }
                else if(R.id.add_requests_item == id){
                    fragmentContainer.setCurrentItem(2);
                }
                else if(R.id.hospitals_item == id){
                    fragmentContainer.setCurrentItem(3);
                }
                return true;
            }
        });
        fragmentContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    bottomNavigationView.getMenu().findItem(R.id.profile_item).setChecked(true);
                }else if(position == 1){
                    bottomNavigationView.getMenu().findItem(R.id.show_requests_item).setChecked(true);
                }else if(position == 2){
                    bottomNavigationView.getMenu().findItem(R.id.add_requests_item).setChecked(true);
                }else if(position == 3){
                    bottomNavigationView.getMenu().findItem(R.id.hospitals_item).setChecked(true);
                }
            }
        });


        //        btnLogOut=findViewById(R.id.LogoutBtn);
//        profileBtn = findViewById(R.id.MainProfileBtn);
//        btnMaps = findViewById(R.id.MapsBtn);
//        btnMaps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PatientProfileMainActivity.this,MapsActivity.class);
//                startActivity(intent);
//            }
//        });
//        /****************************profile user name and age update**********************************/
//        profileBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PatientProfileMainActivity.this,ProfileActivity.class);
//                startActivity(intent);
//
//            }
//        });
//        /****************************profile user name and age update**********************************/
//        /****************************Logout Button**********************************/
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                Toast.makeText(PatientProfileMainActivity.this,"Loged out",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(PatientProfileMainActivity.this,SignIn_SingUp_Activity.class);
//                startActivity(intent);
//                PatientProfileMainActivity.this.finish();
//
//            }
//        });
//        /****************************Logout Button**********************************/

    }


}