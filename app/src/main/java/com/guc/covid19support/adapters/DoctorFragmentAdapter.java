package com.guc.covid19support.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.guc.covid19support.doctor.fragments.DoctorHospitalsFragment;
import com.guc.covid19support.doctor.fragments.DoctorProfileFragment;
import com.guc.covid19support.requests.PatientsRequestsFragment;

public class DoctorFragmentAdapter extends FragmentStateAdapter {
    public DoctorFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment profileFragment = new DoctorProfileFragment();
        Fragment showPatientRequestsFragment = new PatientsRequestsFragment();
        Fragment hospitalFragment = new DoctorHospitalsFragment();
        switch (position){
            case 0:
                return profileFragment;
            case 1:
                return showPatientRequestsFragment;
            case 2:
                return hospitalFragment;
            default:
                return profileFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
