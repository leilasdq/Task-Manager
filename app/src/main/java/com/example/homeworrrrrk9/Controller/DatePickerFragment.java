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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_CRIME_DATE = "com.example.homeworrrrrk9.Controller.crimeDate";
    private AlertDialog mAlertDialog;
    private DatePicker mDatePicker;
    private Date mDate;
    private TaskManager mTaskManager;


    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance() {

        Bundle args = new Bundle();

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker, null, false);

        mTaskManager = new TaskManager();
        mDate = mTaskManager.getDate();
        mDatePicker = view.findViewById(R.id.date_picker);
        initDatePicker();

        mAlertDialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Save Clicked", Toast.LENGTH_SHORT).show();
                        sendResult();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                    }
                })
                .setView(view)
                .create();

        return mAlertDialog;
    }

    private void sendResult() {
        int year = mDatePicker.getYear();
        int monthOfYear = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();

        GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CRIME_DATE, date);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }
}
