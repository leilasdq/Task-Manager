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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_SEND_TIME = "com.example.homeworrrrrk9.Controller.send time";
    private AlertDialog mAlertDialog;
    private TimePicker mTimePicker;
    private Date mDate;
    private TaskManager mTaskManager;
    String time;


    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance() {

        Bundle args = new Bundle();

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null, false);

        mTaskManager = new TaskManager();
        mDate = mTaskManager.getDate();
        mTimePicker = view.findViewById(R.id.time_picker);

        mAlertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setupTime();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                    }
                })
                .create();

        return mAlertDialog;
    }

    private void setupTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = mTimePicker.getCurrentHour();
        int min = mTimePicker.getCurrentMinute();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        //Date date = calendar.getTime();
        SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
        time = mSDF.format(calendar.getTime());
        sendResult(time);
    }

    private void sendResult(String getTime){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SEND_TIME, getTime);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
