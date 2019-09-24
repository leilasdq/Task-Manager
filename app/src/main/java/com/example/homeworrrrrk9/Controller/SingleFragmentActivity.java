package com.example.homeworrrrrk9.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.homeworrrrrk9.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public static final String TAG_LOGIN_AND_SIGN_UP_FRAGMENTS = "login and signUp fragments";

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState==null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, createFragment(), TAG_LOGIN_AND_SIGN_UP_FRAGMENTS)
                    .commit();
        }
    }
}
