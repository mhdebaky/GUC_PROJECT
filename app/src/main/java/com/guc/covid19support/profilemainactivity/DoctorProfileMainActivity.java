package com.guc.covid19support.profilemainactivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.guc.covid19support.R;
import com.guc.covid19support.adapters.DoctorFragmentAdapter;
import com.guc.covid19support.registeration.SignIn_SingUp_Activity;

public class DoctorProfileMainActivity extends AppCompatActivity {
    DoctorFragmentAdapter fragmentAdapter;
    BottomNavigationView bottomNavigationView;
    ViewPager2 fragmentContainer;

    public final static String EXTRA_CONTACT = "CONTACT";
    public final static String EXTRA_IP = "IP";
    public final static String EXTRA_DISPLAYNAME = "DISPLAYNAME";
    public final static String EXTRA_USER = "PATIENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_main);
        Toast.makeText(DoctorProfileMainActivity.this,"Logged in Successfuly",Toast.LENGTH_LONG).show();
        fragmentAdapter = new DoctorFragmentAdapter(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_doctor);
        fragmentContainer = findViewById(R.id.fragment_container_doctor);
        fragmentContainer.setAdapter(fragmentAdapter);
        bottomNavigationView.setOnItemSelectedListener(navItemListener());
        fragmentContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    bottomNavigationView.getMenu().findItem(R.id.profile_item).setChecked(true);
                }else if(position == 1){
                    bottomNavigationView.getMenu().findItem(R.id.show_requests_item).setChecked(true);
                }else if(position == 2){
                    bottomNavigationView.getMenu().findItem(R.id.hospitals_item).setChecked(true);
                }
            }
        });

    }
    public NavigationBarView.OnItemSelectedListener navItemListener(){
        return item -> {
            int id = item.getItemId();
            if(id == R.id.profile_item){
                fragmentContainer.setCurrentItem(0);
            }else if(id == R.id.show_requests_item){
                fragmentContainer.setCurrentItem(1);
            }else if(id == R.id.hospitals_item){
                fragmentContainer.setCurrentItem(2);
            }
            return true;
        };
    }

}