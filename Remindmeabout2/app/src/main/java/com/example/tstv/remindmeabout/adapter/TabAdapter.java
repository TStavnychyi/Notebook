package com.example.tstv.remindmeabout.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.tstv.remindmeabout.fragment.CurrentTaskFragment;
import com.example.tstv.remindmeabout.fragment.DoneTaskFragment;

/**
 * Created by tstv on 30.03.2017.
 */

public class TabAdapter extends FragmentPagerAdapter {

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment mCurrentTaskFragment;
    private DoneTaskFragment mDoneTaskFragment;

    private int numberOfTabs;

    public TabAdapter(FragmentManager fm,int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        mCurrentTaskFragment = new CurrentTaskFragment();
        mDoneTaskFragment = new DoneTaskFragment();
    }


    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                return mCurrentTaskFragment;
            case 1:
                return mDoneTaskFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
