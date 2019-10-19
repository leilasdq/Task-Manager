package com.example.homeworrrrrk9.Controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.State;
import com.example.homeworrrrrk9.Utils.PictureUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowItemFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG_SHOW_DATE_PICKER = "Show date Picker";
    public static final String TAG_SHOW_TIME_PICKER = "Show time picker";
    public static final int GET_DATE_REQUEST_CODE = 2;
    public static final int GET_TIME_REQUEST_CODE = 3;
    public static final int REQUEST_IMAGE_CAPTURE = 4;
    public static final String EXTRA_FORCE_NOTIFY = "Force notify";
    public static final String BUNDLE_TITLE_TEXT = "Title text";
    public static final String BUNDLE_DETAIL_TEXT = "Detail text";
    public static final String BUNDLE_DATE_BTN_TEXT = "Date btn text";
    public static final String BUNDLE_TIME_BTN_TEXT = "Time btn text";
    public static final String BUNDLE_SPINNER_POSITION = "Spinner position";

    String TAG = "Dialog fragment";
    private AlertDialog mDialogFragment;

    private TextInputLayout title;
    private TextInputLayout description;
    private Button date;
    private Button time;
    private ImageButton takePhoto;
    private ImageView setPhoto;
    private Spinner spinner;
    private List<String> mStatusSpinnerItems;

    private TaskManager mTaskManager;
    private DateFormat dateFormat;
    private DateFormat timeFormat;
    private String dateStr;
    private String timeStr;
    private State mState;
    private File photoFile;


    public ShowItemFragment() {
        // Required empty public constructor
    }

    public static ShowItemFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ShowItemFragment fragment = new ShowItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_item, null, false);

        initViews(view);
        spinnerSetup();

        if (savedInstanceState!=null){
            title.getEditText().setText(savedInstanceState.getString(BUNDLE_TITLE_TEXT));
            description.getEditText().setText(savedInstanceState.getString(BUNDLE_DETAIL_TEXT));
            date.setText(savedInstanceState.getString(BUNDLE_DATE_BTN_TEXT));
            time.setText(savedInstanceState.getString(BUNDLE_TIME_BTN_TEXT));
            spinner.setSelection(savedInstanceState.getInt(BUNDLE_SPINNER_POSITION, 0));
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

        setUpListeners();

        return mDialogFragment;
    }

    private void setUpListeners() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e(TAG, "onClick: Date clicked" );
                //Toast.makeText(getActivity(), "Date btn Clicked", Toast.LENGTH_SHORT).show();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTaskManager.getDate());
                datePickerFragment.setTargetFragment(ShowItemFragment.this, GET_DATE_REQUEST_CODE);
                datePickerFragment.show(getFragmentManager(), TAG_SHOW_DATE_PICKER);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Time btn Clicked", Toast.LENGTH_SHORT).show();
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(ShowItemFragment.this, GET_TIME_REQUEST_CODE);
                timePickerFragment.show(getFragmentManager(), TAG_SHOW_TIME_PICKER);
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = TasksRepository.getInstance(getContext()).getPhotoFile(mTaskManager);
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.homeworrrrrk9",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
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
                        mTaskManager.setState(mState);
                        mTaskManager.setUserId(ListsActivity.getUserId());

                        wantToCloseDialog = true;
                    }
                    if(wantToCloseDialog){
                        setDate();
                        TasksRepository.addTodoItem(mTaskManager);
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_FORCE_NOTIFY, true);
                        Fragment fragment = getTargetFragment();
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        d.dismiss();
                    }
                }
            });
        }
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

    private void initViews(View view) {
        mTaskManager = new TaskManager();
        mStatusSpinnerItems = new ArrayList<>();
        title = view.findViewById(R.id.title_editText);
        description = view.findViewById(R.id.des_editText);
        spinner = view.findViewById(R.id.done_spinner);
        date = view.findViewById(R.id.date_btn);
        time = view.findViewById(R.id.time_btn);
        takePhoto = view.findViewById(R.id.task_image_btn);
        setPhoto = view.findViewById(R.id.task_image);

        dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
        timeFormat = new SimpleDateFormat("hh:mm a");

        if (mTaskManager!=null) {
            title.getEditText().setText(mTaskManager.getTitle());
            description.getEditText().setText(mTaskManager.getDetail());
            date.setText(dateFormat.format(mTaskManager.getDate()));
            time.setText(timeFormat.format(mTaskManager.getDate()));
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

            if (requestCode == GET_DATE_REQUEST_CODE) {
                Date getDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_SEND_DATE);
                dateStr = dateFormat.format(getDate);
                date.setText(dateStr);
            }
            if (requestCode == GET_TIME_REQUEST_CODE) {
                timeStr = data.getStringExtra(TimePickerFragment.EXTRA_SEND_TIME);
                time.setText(timeStr);
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE ) {
                updatePhotoFormat();
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                setPhoto.setImageBitmap(imageBitmap);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().recreate();
//        Intent intent = new Intent(getContext(), ListsActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }

    private void setDate() {
        dateStr = date.getText().toString();
        timeStr = time.getText().toString();
        DateFormat dft = new SimpleDateFormat("EEE, MMM d yyyy hh:mm a");
        String date = dateStr + " " +  timeStr;
        Date d = null;
        try {
            d = dft.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//            Log.e(TAG, "onActivityResult: " + d );
        mTaskManager.setDate(d);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        if (item.equals(State.DONE.name())) {
            mState = State.DONE;
        }
        else if (item.equals(State.TODO.name())){
            mState = State.TODO;
        }
        else if (item.equals(State.DOING.name())){
            mState = State.DOING;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mState = State.TODO;
    }

    private void updatePhotoFormat(){
        if (photoFile==null | !photoFile.exists()){
            return;
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getAbsolutePath(), getActivity());
            setPhoto.setImageBitmap(bitmap);
            mTaskManager.setPhotoPath(photoFile.getAbsolutePath());
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
        outState.putSerializable("save date", dateStr);
    }
}
