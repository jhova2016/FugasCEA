package com.example.fugascea.controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fugascea.Fragment_Conclueded;
import com.example.fugascea.Fragment_Leaks;

public class PagerControler extends FragmentPagerAdapter {

    int numeroftabs;

    public PagerControler(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numeroftabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_Leaks();

            case 1:
                return new Fragment_Conclueded();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numeroftabs;
    }
}

