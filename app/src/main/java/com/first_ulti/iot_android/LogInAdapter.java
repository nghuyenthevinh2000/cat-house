package com.first_ulti.iot_android;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LogInAdapter extends FragmentStateAdapter {

    private LogInAdapter(FragmentActivity fragment){
        super(fragment);
    }

    public static LogInAdapter newInstance(FragmentActivity fragment){
        LogInAdapter obj = new LogInAdapter(fragment);

        return obj;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = LoginFragment.newInstance();
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}