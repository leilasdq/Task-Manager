package com.example.homeworrrrrk9.Controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.homeworrrrrk9.Model.User;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.Repository.UserRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public static final String BUNDLE_NAME_STRING = "name string";
    public static final String BUNDLE_PASSWORD_STRING = "Password string";

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

    String saveName = "";
    String savePass = "";

    List<User> mUsers;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(ARGS_USERNAME_TEXT, user);
        args.putString(ARGS_PASSWORD_TEXT, pass);

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initViews(view);
        initListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUsers = UserRepository.getInstance(getActivity()).getRepositoryList();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveName = mUserInput.getEditText().getText().toString();
        savePass = mPasswordInput.getEditText().getText().toString();
    }

    private void initListeners() {
        mSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUSerLogin(mUserInput.getEditText().getText().toString())) {
                    User user = new User();
                    user.setUsername(mUserInput.getEditText().getText().toString());
                    user.setPassword(mPasswordInput.getEditText().getText().toString());
                    UserRepository.addUsers(user);
                    sendResult();
                }
                else {
                    Snackbar.make(getView(), "This username had been taken\nChoose another one or login to your account.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initViews(View view) {
        mUserInput = view.findViewById(R.id.text_user_name);
        mPasswordInput = view.findViewById(R.id.text_password);
        mSignButton = view.findViewById(R.id.signUp_btn);

        if (saveName.isEmpty()){
            mUserInput.getEditText().setText(getUserText);
        } else {
            mUserInput.getEditText().setText(saveName);
        }
        if (savePass.isEmpty()) {
            mPasswordInput.getEditText().setText(getPassText);
        } else {
            mPasswordInput.getEditText().setText(savePass);
        }

    }

    private void sendResult() {
        if (!userValidate() | !passwordValidate()) {
            Snackbar.make(getView(), "Fill the realignments..", Snackbar.LENGTH_LONG).show();
        } else {
            userName = mUserInput.getEditText().getText().toString();
            password = mPasswordInput.getEditText().getText().toString();

            Intent intent = new Intent();
            intent.putExtra(EXTRA_USER_NAME, userName);
            intent.putExtra(EXTRA_PASS_WORD, password);

            Fragment fragment = getTargetFragment();
            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            getFragmentManager().beginTransaction().replace(R.id.container, getTargetFragment()).commit();
        }
    }

    private boolean userValidate() {
        String userText = mUserInput.getEditText().getText().toString();

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
        String passText = mPasswordInput.getEditText().getText().toString();

        if (passText.isEmpty()) {
            mPasswordInput.setError("Field can not be empty");
            return false;
        } else {
            mPasswordInput.setError(null);
            return true;
        }
    }

    private boolean validateUSerLogin(String userText){
        for (int i = 0; i < mUsers.size() ; i++) {
            if (mUsers.get(i).getUsername().equalsIgnoreCase(userText)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_NAME_STRING, saveName);
        outState.putString(BUNDLE_PASSWORD_STRING, savePass);
    }
}
