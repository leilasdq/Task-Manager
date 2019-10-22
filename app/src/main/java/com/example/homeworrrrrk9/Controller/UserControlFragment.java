package com.example.homeworrrrrk9.Controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homeworrrrrk9.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserControlFragment extends Fragment {


    public UserControlFragment() {
        // Required empty public constructor
    }

    public static UserControlFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserControlFragment fragment = new UserControlFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_controll, container, false);
    }

}
