package com.example.homeworrrrrk9.Controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.homeworrrrrk9.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final int LOGIN_FRAGMENT_REQUEST_CODE = 1;
    public static final String BUNDLE_NAME_STRING = "name string";
    private final String FRAGMENT_TAG_SIGN_UP_FRAGMENT = "sign up fragment";

    public static final String TAG = "Login fragment";

    private TextInputLayout mUserInput;
    private TextInputLayout mPasswordInput;
    private Button mLoginButton;
    private Button mSignUpButton;

    //// USER AND PASSWORD ON THIS PAGE ////
    private String userText;
    private String passText;

    //// USER AND PASS COME FROM SIGN UP PAGE ////
    String getUserName;
    String getPassword;

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

        initViews(view);
        setUpListeners();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == LOGIN_FRAGMENT_REQUEST_CODE) {

            getUserName = data.getStringExtra(SignUpFragment.EXTRA_USER_NAME);
            getPassword = data.getStringExtra(SignUpFragment.EXTRA_PASS_WORD);
        }
    }

    private void initViews(View view) {
        mUserInput = view.findViewById(R.id.text_input_user_name);
        mPasswordInput = view.findViewById(R.id.text_input_password);
        mLoginButton = view.findViewById(R.id.login_btn);
        mSignUpButton = view.findViewById(R.id.create_account_btn);

        mUserInput.getEditText().setText(getUserName);
        mPasswordInput.getEditText().setText(getPassword);
    }

    private void setUpListeners() {

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userValidate() | !passwordValidate()) {
                    Snackbar.make(getView(), "Fill the realignments..", Snackbar.LENGTH_LONG).show();
                } else if (mUserInput.getEditText().getText().toString().equals(getUserName)
                && mPasswordInput.getEditText().getText().toString().equals(getPassword)){
                    Snackbar.make(getView(), "You are logged in", Snackbar.LENGTH_LONG).show();
                    Intent intent = ListsActivity.newIntent(getActivity());
                    startActivity(intent);
                } else {
                    Snackbar.make(getView(), "invalid username or password", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUserInput.getEditText().getText().toString();
                String password = mPasswordInput.getEditText().getText().toString();

                //Log.e(TAG, "newInstance: user: " + username +"\npass: " + password );

                FragmentManager fragmentManager = getFragmentManager();
                SignUpFragment signUpFragment = SignUpFragment.newInstance(username, password);
                signUpFragment.setTargetFragment(LoginFragment.this, LOGIN_FRAGMENT_REQUEST_CODE);

                //Log.e(TAG, "onClick: TargetFragment: " + signUpFragment.getTargetFragment());

                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, signUpFragment, FRAGMENT_TAG_SIGN_UP_FRAGMENT)
                        .commit();
            }
        });
    }

    private boolean userValidate() {
        userText = mUserInput.getEditText().getText().toString();

        if (userText.isEmpty()) {
            mUserInput.setError("Field can not be empty");
            return false;
        }
        if (userText.length() > 15) {
            mUserInput.setError("Too long text");
            return false;
        } else {
            mUserInput.setError(null);
            return true;
        }
    }

    private boolean passwordValidate() {
        passText = mPasswordInput.getEditText().getText().toString();

        if (passText.isEmpty()) {
            mPasswordInput.setError("Field can not be empty");
            return false;
        } else {
            mPasswordInput.setError(null);
            return true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_NAME_STRING, mUserInput.getEditText().getText().toString());
        outState.putString(BUNDLE_NAME_STRING, mUserInput.getEditText().getText().toString());
    }
}
