package com.example.homeworrrrrk9;

import com.example.homeworrrrrk9.Controller.DoingFragment;
import com.example.homeworrrrrk9.Controller.DoneFragment;
import com.example.homeworrrrrk9.Controller.TodoFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TaskManagerFragmentPagerAdapter extends FragmentStatePagerAdapter {


    public TaskManagerFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return TodoFragment.newInstance();
        }
        else if (position == 1){
            return DoingFragment.newInstance();
        }
        else {
            return DoneFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TODO";
            case 1:
                return "DOING";
            case 2:
                return "DONE";
            default:
                return "";
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
