package com.example.homeworrrrrk9.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.homeworrrrrk9.R;

public class ListsActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        return new Intent(context, ListsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
    }
}
