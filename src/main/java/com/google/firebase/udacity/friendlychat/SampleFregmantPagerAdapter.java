package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SampleFregmantPagerAdapter extends FragmentPagerAdapter {

    private final int pageCount = 3;
    private String[] titles = new String[]{"Rooms", "Camra", "Log"};
    private Context context;
    private boolean localConnection;

    public SampleFregmantPagerAdapter(FragmentManager fm, Context context, boolean localConnection) {
        super(fm);
        this.context = context;
        this.localConnection = localConnection;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int i) {
        if (localConnection) {
            if (i == 0) {
                return new RoomsFragment();
            } else if (i == 1) {
                return new LocalCamraFragment();
            } else {
                return new LocalLogFragment();
            }
        }
        else {
            if (i == 0) {
                return new RoomsFragment();
            } else if (i == 1) {
                return new CloudCamraFragment();
            } else  {
                return new CloudLogFragment();
            }
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
