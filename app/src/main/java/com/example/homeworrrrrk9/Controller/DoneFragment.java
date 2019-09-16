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
public class DoneFragment extends Fragment {

    public static DoneFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DoneFragment fragment = new DoneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public DoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false);
    }

}
