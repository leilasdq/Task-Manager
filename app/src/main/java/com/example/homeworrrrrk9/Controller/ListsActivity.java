package com.example.homeworrrrrk9.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.TaskManagerFragmentPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.zip.Inflater;

public class ListsActivity extends AppCompatActivity {

    public static final String TAG_ADD_ITEM_FRAGMENTS = "Add item fragments";
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

//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(ListsActivity.this, "Add clicked", Toast.LENGTH_SHORT).show();
//                ShowItemFragment itemFragment = ShowItemFragment.newInstance();
//                itemFragment.show(getSupportFragmentManager(), TAG_ADD_ITEM_FRAGMENTS);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.account:
                Toast.makeText(this, "Account clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews(){
        mViewPager = findViewById(R.id.task_view_pager);
        //addBtn = findViewById(R.id.floatingActionButton);
        mTabLayout = findViewById(R.id.task_tab_layout);

        TaskManagerFragmentPagerAdapter adapter = new TaskManagerFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
