package com.example.homeworrrrrk9.Controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.State;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowItemFragment extends DialogFragment {
    public static final String ARG_UUID_ARGUMENT = "uuid argument";
    public static final String TAG_SHOW_DATE_PICKER = "Show date Picker";
    public static final String TAG_SHOW_TIME_PICKER = "Show time picker";
    public static final int GET_DATE_REQUEST_CODE = 2;
    public static final int GET_TIME_REQUEST_CODE = 3;
    String TAG = "Dialog fragment";
    private AlertDialog mDialogFragment;

    private TextInputLayout title;
    private TextInputLayout description;
    private Button date;
    private Button time;
    private Spinner done;

    private TaskManager mTaskManager;
    UUID mUUID;
    List<TaskManager> mTaskManagers;
    TaskManager taskManager;
    DateFormat dateFormat;
    DateFormat timeFormat;
    String dateStr;
    String timeStr;


    public ShowItemFragment() {
        // Required empty public constructor
    }

    public static ShowItemFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ShowItemFragment fragment = new ShowItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowItemFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_UUID_ARGUMENT, id);

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            UUID id = (UUID) getArguments().getSerializable(ARG_UUID_ARGUMENT);
            taskManager = TasksRepository.getTask(id);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_item, null, false);

        initViews(view);

        if (getArguments()!=null){
            mUUID = (UUID) getArguments().getSerializable(ARG_UUID_ARGUMENT);
        }

        mDialogFragment = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialogFragment.cancel();
                    }
                }).setView(view)
                .create();

        date = view.findViewById(R.id.date_btn);
        time = view.findViewById(R.id.time_btn);
        setUpListeners();

        return mDialogFragment;
    }

    private void setUpListeners() {
        date.setText(dateFormat.format(mTaskManager.getDate()));
        time.setText(timeFormat.format(mTaskManager.getDate()));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: Date clicked" );
                Toast.makeText(getActivity(), "Date btn Clicked", Toast.LENGTH_SHORT).show();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(ShowItemFragment.this, GET_DATE_REQUEST_CODE);
                datePickerFragment.show(getFragmentManager(), TAG_SHOW_DATE_PICKER);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Time btn Clicked", Toast.LENGTH_SHORT).show();
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(ShowItemFragment.this, GET_TIME_REQUEST_CODE);
                timePickerFragment.show(getFragmentManager(), TAG_SHOW_TIME_PICKER);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null) {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog;
                    if (!titleValidate() | !detailValidate()){
                        Toast.makeText(getContext(), "Fill required fields", Toast.LENGTH_SHORT).show();
                        wantToCloseDialog = false;
                    }
                    else{
                        mTaskManager.setTitle(title.getEditText().getText().toString());
                        mTaskManager.setDetail(description.getEditText().getText().toString());
                        mTaskManager.setState(State.TODO);
                        TasksRepository.addTodoItem(mTaskManager);

                        wantToCloseDialog = true;
                    }
                    if(wantToCloseDialog){
//                        getFragmentManager().beginTransaction().remove(ShowItemFragment.this).commit();
//                        getFragmentManager().beginTransaction().attach(TodoFragment.newInstance()).commit();
                        d.dismiss();
                    }
                }
            });
        }
    }

    private void initViews(View view) {
        title = view.findViewById(R.id.title_editText);
        description = view.findViewById(R.id.des_editText);
        done = view.findViewById(R.id.done_spinner);
        mTaskManagers = TasksRepository.getInstance().getRepositoryList();

        dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
        timeFormat = new SimpleDateFormat("hh:mm a");

        mTaskManager = new TaskManager();
//        dateStr = dateFormat.format(mTaskManager.getDate());
//        timeStr = timeFormat.format(mTaskManager.getDate());

        if (taskManager!=null) {
            title.getEditText().setText(taskManager.getTitle());
            description.getEditText().setText(taskManager.getDetail());
        }
    }

    private boolean titleValidate() {
        String titleText = title.getEditText().getText().toString();

        if (titleText.isEmpty()) {
            title.setError("Field can not be empty");
            return false;
        } else {
            title.setError(null);
            return true;
        }
    }

    private boolean detailValidate() {
        String detailText = description.getEditText().getText().toString();

        if (detailText.isEmpty()) {
            description.setError("Field can not be empty");
            return false;
        } else {
            description.setError(null);
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        else {
            dateStr = date.getText().toString();
            timeStr = time.getText().toString();
            if (requestCode == GET_DATE_REQUEST_CODE) {
                Date getDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_SEND_DATE);
                dateStr = dateFormat.format(getDate);
                date.setText(dateStr);
            }
            if (requestCode == GET_TIME_REQUEST_CODE) {
                timeStr = data.getStringExtra(TimePickerFragment.EXTRA_SEND_TIME);
                time.setText(timeStr);
            }
            DateFormat dft = new SimpleDateFormat("EEE, MMM d yyyy hh:mm a");
            String date = dateStr + " " +  timeStr;
            Date d = null;
            try {
                d = dft.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mTaskManager.setDate(d);
        }
    }
}
