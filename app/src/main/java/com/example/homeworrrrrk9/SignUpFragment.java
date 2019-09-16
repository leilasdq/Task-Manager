package com.example.homeworrrrrk9;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    public static final String ARGS_USERNAME_TEXT = "username text";
    public static final String ARGS_PASSWORD_TEXT = "password text";

    public static final String TAG = "SignUp fragment";
    public static final String EXTRA_USER_NAME = "user name";
    public static final String EXTRA_PASS_WORD = "pass word";

    private TextInputLayout mUserInput;
    private TextInputLayout mPasswordInput;
    private Button mSignButton;

    String getUserText;
    String getPassText;

    String userName;
    String password;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(ARGS_USERNAME_TEXT, user);
        args.putString(ARGS_PASSWORD_TEXT, pass);

//        Log.e(TAG, "newInstance: user: " + user + "\npass: " + pass);

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            getUserText = getArguments().getString(ARGS_USERNAME_TEXT);
            getPassText = getArguments().getString(ARGS_PASSWORD_TEXT);
        }

//        Log.e(TAG, "newInstance: user: " + getUserText + "\npass: " + getPassText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initViews(view);

        mSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = mUserInput.getEditText().getText().toString();
                password = mPasswordInput.getEditText().getText().toString();

                sendResult();
            }
        });


        return view;
    }

    private void initViews(View view) {
        mUserInput = view.findViewById(R.id.text_user_name);
        mPasswordInput = view.findViewById(R.id.text_password);
        mSignButton = view.findViewById(R.id.signUp_btn);

        mUserInput.getEditText().setText(getUserText);
        mPasswordInput.getEditText().setText(getPassText);

    }

    private void sendResult() {
        userName = mUserInput.getEditText().getText().toString();
        password = mPasswordInput.getEditText().getText().toString();
        Log.e(TAG, "sendResult: user: " + userName + "\npass: " + password );

        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_PASS_WORD, password);

        Fragment fragment = getTargetFragment();
//        Log.e(TAG, "sendResult: Target Fragment: " + getTargetFragment() );
        getFragmentManager().beginTransaction().replace(R.id.container, getTargetFragment()).commit();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

//        getFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment)
//                .commit();

    }

}
