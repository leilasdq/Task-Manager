package com.example.homeworrrrrk9;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userValidate() | !passwordValidate()){
                    Snackbar.make(getView(), "Fill the realignments..", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(getView(), "You are logged in", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private boolean userValidate() {
        String userText = mUserInput.getEditText().getText().toString();

        if (userText.isEmpty()) {
            mUserInput.setError("Field can not be empty");
            return false;
        } if (userText.length() > 15){
            mUserInput.setError("Too long text");
            return false;
        } else {
            mUserInput.setError(null);
            return true;
        }
    }

    private boolean passwordValidate(){
        String passText = mPasswordInput.getEditText().getText().toString();

        if (passText.isEmpty()) {
            mPasswordInput.setError("Field can not be empty");
            return false;
        } else {
            mPasswordInput.setError(null);
            return true;
        }
    }


}
