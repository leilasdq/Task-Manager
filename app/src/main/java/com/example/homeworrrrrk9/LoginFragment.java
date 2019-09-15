package com.example.homeworrrrrk9;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private TextInputLayout mUserInput;
    private TextInputLayout mPasswordInput;
    private Button mLoginButton;
    private Button mSignUpButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mUserInput = view.findViewById(R.id.text_input_user_name);
        mPasswordInput = view.findViewById(R.id.text_input_password);
        mLoginButton = view.findViewById(R.id.login_btn);
        mSignUpButton = view.findViewById(R.id.create_account_btn);

        return view;
    }

}
