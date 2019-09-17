package com.example.homeworrrrrk9.Controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowItemFragment extends DialogFragment {
    public static final String ARG_UUID_ARGUMENT = "uuid argument";
    private AlertDialog mDialogFragment;

    private TextInputLayout title;
    private TextInputLayout description;
    private Button date;
    private Button time;
    private CheckBox done;

    private TaskManager mTaskManager;


    public ShowItemFragment() {
        // Required empty public constructor
    }

    public static ShowItemFragment newInstance() {
        
        Bundle args = new Bundle();
//        args.putSerializable(ARG_UUID_ARGUMENT, uuid);
        
        ShowItemFragment fragment = new ShowItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_show_item, container, false);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_item, null, false);

        initViews(view);
        mTaskManager = new TaskManager();

        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        date.setText(dateFormat.format(mTaskManager.getDate()));
        time.setText(timeFormat.format(mTaskManager.getDate()));

        mDialogFragment = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialogFragment.cancel();
                    }
                }).setView(view)
                .create();

        return mDialogFragment;
    }

    private void initViews(View view) {
        title = view.findViewById(R.id.title_editText);
        description = view.findViewById(R.id.des_editText);
        date = view.findViewById(R.id.date_btn);
        time = view.findViewById(R.id.time_btn);
        done = view.findViewById(R.id.done_check);
    }
}
