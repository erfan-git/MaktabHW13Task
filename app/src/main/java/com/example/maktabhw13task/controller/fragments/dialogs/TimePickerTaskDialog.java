package com.example.maktabhw13task.controller.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.maktabhw13task.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePickerTaskDialog extends DialogFragment {

    public static final String ARG_TIME = "ArgTime";

    public static TimePickerTaskDialog newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerTaskDialog fragment = new TimePickerTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String EXTRA_USER_SELECTED_TIME = "com.example.maktabhw13task.controller.fragments.ExtraUserSelectedTime";
    private TimePicker mTimePicker;
    private Date mDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = (Date) getArguments().getSerializable(ARG_TIME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_task_time_picker, null);
        findViews(view);

        setCurrentTime();

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.setTime(mDate);
                        gc.set(Calendar.HOUR, mTimePicker.getHour());
                        gc.set(Calendar.MINUTE, mTimePicker.getMinute());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtra(EXTRA_USER_SELECTED_TIME, gc));
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view)
                .setTitle("Time Picker")
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setCurrentTime() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(mDate);
        mTimePicker.setHour(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setMinute(gregorianCalendar.get(Calendar.MINUTE));
    }

    private void findViews(View view){
        mTimePicker = view.findViewById(R.id.timePicker);
    }
}