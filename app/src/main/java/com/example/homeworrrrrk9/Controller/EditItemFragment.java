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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditItemFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG_SHOW_DATE_PICKER = "Show date Picker";
    public static final String TAG_SHOW_TIME_PICKER = "Show time picker";
    public static final int GET_DATE_REQUEST_CODE = 10;
    public static final int GET_TIME_REQUEST_CODE = 11;
    public static final String ARGS_TASK_MANAGER_MODEL = "Task manager model";
    public static final String BUNDLE_TITLE_TEXT = "Title text";
    public static final String BUNDLE_DETAIL_TEXT = "Detail text";
    public static final String BUNDLE_DATE_BTN_TEXT = "Date btn text";
    public static final String BUNDLE_TIME_BTN_TEXT = "Time btn text";
    public static final String BUNDLE_SPINNER_POSITION = "Spinner position";
    String TAG = "Edit";

    private AlertDialog mDialogFragment;

    private TextInputLayout title;
    private TextInputLayout description;
    private Button date;
    private Button time;
    private Spinner spinner;
    private List<String> mStatusSpinnerItems;

    private TaskManager mTaskManager;
    TaskManager taskManager;
    private DateFormat dateFormat;
    private DateFormat timeFormat;
    private String dateStr;
    private String timeStr;
    private Date tempDate;
    private State mState;


    public EditItemFragment() {
        // Required empty public constructor
    }

    public static EditItemFragment newInstance(TaskManager taskModel) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_MANAGER_MODEL, taskModel);

        EditItemFragment fragment = new EditItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_item, null, false);

        initViews(view);
        spinnerSetup();

        if (getArguments() != null) {
            taskManager = (TaskManager) getArguments().getSerializable(ARGS_TASK_MANAGER_MODEL);
            title.getEditText().setText(taskManager.getTitle());
            description.getEditText().setText(taskManager.getDetail());
            stDateAndTimeBtn(taskManager);
            spinner.setPrompt(taskManager.getState().toString());
        }

        if (savedInstanceState != null) {
            title.getEditText().setText(savedInstanceState.getString(BUNDLE_TITLE_TEXT));
            description.getEditText().setText(savedInstanceState.getString(BUNDLE_DETAIL_TEXT));
            date.setText(savedInstanceState.getString(BUNDLE_DATE_BTN_TEXT));
            time.setText(savedInstanceState.getString(BUNDLE_TIME_BTN_TEXT));
            spinner.setSelection(savedInstanceState.getInt(BUNDLE_SPINNER_POSITION, 0));
        }

        mDialogFragment = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Edit", null)
                .setNegativeButton("Delete", null)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialogFragment.cancel();
                    }
                })
                .setView(view)
                .create();

        setUpListeners();

        return mDialogFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog;
                    if (!titleValidate() | !detailValidate()) {
                        Toast.makeText(getContext(), "Fill required fields", Toast.LENGTH_SHORT).show();
                        wantToCloseDialog = false;
                    } else {
//                        TasksRepository.editItem(taskManager);
//                        TasksRepository.deleteItem(taskManager);
                        mTaskManager.setTitle(title.getEditText().getText().toString());
                        mTaskManager.setDetail(description.getEditText().getText().toString());
                        mTaskManager.setState(mState);
//                        TasksRepository.addTodoItem(mTaskManager);

                        wantToCloseDialog = true;
                    }
                    if (wantToCloseDialog) {
                        setDate();
                        TasksRepository.editItem(mTaskManager);
                        d.dismiss();
                    }
                }
            });
            Button negativeButton = d.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TasksRepository.deleteItem(taskManager);
                    d.dismiss();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;


        dateStr = date.getText().toString();
        timeStr = time.getText().toString();
        if (requestCode == GET_DATE_REQUEST_CODE) {
            Date getDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_SEND_DATE);
            dateStr = dateFormat.format(getDate);
            date.setText(dateStr);
        }
        if (requestCode == GET_TIME_REQUEST_CODE) {
            tempDate = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_SEND_TIME);
            DateFormat df = new SimpleDateFormat("hh:mm a");
            timeStr = df.format(tempDate);
            time.setText(timeStr);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().recreate();
    }

    private void setDate() {
        dateStr = date.getText().toString();
        timeStr = time.getText().toString();
        DateFormat dft = new SimpleDateFormat("EEE, MMM d yyyy hh:mm a");
        String date = dateStr + " " + timeStr;
        Date d = null;
        try {
            d = dft.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//            Log.e(TAG, "onActivityResult: " + d );
        mTaskManager.setDate(d);
    }

    private void stDateAndTimeBtn(TaskManager taskManager) {
        Date getDate = taskManager.getDate();
        String[] dateString = getDate.toString().split(" ");
        String myDate = dateString[0] + ", " + dateString[1] + " " + dateString[2] + " " + dateString[dateString.length - 1];
        String myTime = dateString[3];
        String[] changeDate = myTime.split(":");
        int changeDateFormat = Integer.parseInt(changeDate[0]);
        boolean isAM = true;
        if (changeDateFormat > 12) {
            changeDateFormat -= 12;
            isAM = false;
        }
        String am;
        if (isAM) am = " AM";
        else am = " PM";
        String setFormatTime = String.valueOf(changeDateFormat) + ":" + changeDate[1] + am;
//        Log.e(TAG, "onCreateDialog: my time: " + myTime );
        date.setText(myDate);
        time.setText(setFormatTime);
    }

    private void initViews(View view) {
        mStatusSpinnerItems = new ArrayList<>();
        title = view.findViewById(R.id.title_editText);
        description = view.findViewById(R.id.des_editText);
        spinner = view.findViewById(R.id.done_spinner);
        date = view.findViewById(R.id.date_btn);
        time = view.findViewById(R.id.time_btn);
        dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
        timeFormat = new SimpleDateFormat("hh:mm a");

        mTaskManager = new TaskManager();

        if (mTaskManager != null) {
            title.getEditText().setText(mTaskManager.getTitle());
            description.getEditText().setText(mTaskManager.getDetail());
        }
    }

    private void setUpListeners() {

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Date btn Clicked", Toast.LENGTH_SHORT).show();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(taskManager.getDate());
                datePickerFragment.setTargetFragment(EditItemFragment.this, GET_DATE_REQUEST_CODE);
                datePickerFragment.show(getFragmentManager(), TAG_SHOW_DATE_PICKER);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Time btn Clicked", Toast.LENGTH_SHORT).show();
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(EditItemFragment.this, GET_TIME_REQUEST_CODE);
                timePickerFragment.show(getFragmentManager(), TAG_SHOW_TIME_PICKER);
            }
        });
    }

    private void spinnerSetup() {
        mStatusSpinnerItems.add(String.valueOf(State.TODO));
        mStatusSpinnerItems.add(String.valueOf(State.DOING));
        mStatusSpinnerItems.add(String.valueOf(State.DONE));

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, mStatusSpinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        if (item.equals(State.DONE.name())) {
            mState = State.DONE;
        } else if (item.equals(State.TODO.name())) {
            mState = State.TODO;
        } else if (item.equals(State.DOING.name())) {
            mState = State.DOING;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_TITLE_TEXT, title.getEditText().getText().toString());
        outState.putString(BUNDLE_DETAIL_TEXT, description.getEditText().getText().toString());
        outState.putString(BUNDLE_DATE_BTN_TEXT, date.getText().toString());
        outState.putString(BUNDLE_TIME_BTN_TEXT, time.getText().toString());
        outState.putInt(BUNDLE_SPINNER_POSITION, spinner.getSelectedItemPosition());
    }
}
