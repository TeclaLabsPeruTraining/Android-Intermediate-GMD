package com.bx.android.lesson1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bx.android.lesson1.fragments.ActivityFragment;
import com.bx.android.lesson1.fragments.InboxFragment;
import com.bx.android.lesson1.fragments.TimeLineFragment;

/**
 * Created by pjohnson on 20/02/17.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimeLineFragment.newInstance();
            case 1:
                return ActivityFragment.newInstance();
            case 2:
                return InboxFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
