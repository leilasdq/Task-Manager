package com.example.homeworrrrrk9.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.homeworrrrrk9.R;

public class UserControlActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return UserControlFragment.newInstance();
    }

    public  static Intent newIntent(Context context){
        return  new Intent(context, UserControlActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fragments);
    }
}
