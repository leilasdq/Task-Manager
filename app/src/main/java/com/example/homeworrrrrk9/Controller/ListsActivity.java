package com.example.homeworrrrrk9.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.TaskManagerFragmentPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.zip.Inflater;

public class ListsActivity extends AppCompatActivity {

    public static final String TAG_ADD_ITEM_FRAGMENTS = "Add item fragments";
    public static final String EXTRA_USER_NAME = "com.example.homeworrrrrk9.Controller.UserName";
    public static final String EXTRA_PASS_WORD = "com.example.homeworrrrrk9.Controller.PassWord";
    ViewPager mViewPager;
    TabLayout mTabLayout;
    String user;
    String pass;
    FloatingActionButton addBtn;

    public static Intent newIntent(Context context, String userName, String passWord){
        Intent intent = new Intent(context, ListsActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_PASS_WORD, passWord);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        initViews();
        user = getIntent().getStringExtra(EXTRA_USER_NAME);
        pass = getIntent().getStringExtra(EXTRA_PASS_WORD);

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
                //Toast.makeText(this, "Account clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.show_account:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Account detail")
                        .setMessage("username: " + user + "\npassword: " + pass)
                        .setNegativeButton("OK", null).create();
                alertDialog.show();
                return true;
            case R.id.log_out:
                System.exit(1);
                return true;
            case R.id.delete_all:
                final List<TaskManager> models = TasksRepository.getInstance().getRepositoryList();
                AlertDialog delete = new AlertDialog.Builder(this)
                        .setTitle("Delete all items")
                        .setMessage("All items will be delete.\nAre you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (models.size()>0){
                                    TasksRepository.deleteAll();
                                } else {
                                    Toast.makeText(ListsActivity.this, "You didn't have any items", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null).create();
                delete.show();
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
