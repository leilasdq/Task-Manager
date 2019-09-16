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
public class TodoFragment extends Fragment {

    public static TodoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TodoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

}
