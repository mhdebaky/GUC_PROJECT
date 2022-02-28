package com.guc.covid19support.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.guc.covid19support.patient.fragments.PatientAddRequestFragment;
import com.guc.covid19support.patient.fragments.PatientHospitalsFragment;
import com.guc.covid19support.patient.fragments.PatientProfileFragment;
import com.guc.covid19support.requests.PatientShowRequestFragment;

public class PatientFragmentAdapter extends FragmentStateAdapter {


    public PatientFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment profileFragment = new PatientProfileFragment();
        Fragment showRequestsFragment = new PatientShowRequestFragment();
        Fragment addRequestFragment = new PatientAddRequestFragment();
        Fragment hospitalFragment = new PatientHospitalsFragment();
        switch (position){
            case 0:
                return profileFragment;
            case 1:
                return showRequestsFragment;
            case 2:
                return addRequestFragment;
            case 3:
                return hospitalFragment;
            default:
                return profileFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
