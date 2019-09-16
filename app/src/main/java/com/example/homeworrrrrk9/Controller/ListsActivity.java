package com.example.homeworrrrrk9.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.TaskManagerFragmentPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ListsActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    FloatingActionButton addBtn;

    public static Intent newIntent(Context context){
        return new Intent(context, ListsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        initViews();
    }

    private void initViews(){
        mViewPager = findViewById(R.id.task_view_pager);
        addBtn = findViewById(R.id.floatingActionButton);
        mTabLayout = findViewById(R.id.task_tab_layout);

        TaskManagerFragmentPagerAdapter adapter = new TaskManagerFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
