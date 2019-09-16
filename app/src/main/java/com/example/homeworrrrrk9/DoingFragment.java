package com.example.homeworrrrrk9;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoingFragment extends Fragment {

    public static DoingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DoingFragment fragment = new DoingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public DoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doing, container, false);
    }

}
