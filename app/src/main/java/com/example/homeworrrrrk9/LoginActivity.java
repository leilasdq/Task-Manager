package com.example.homeworrrrrk9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
