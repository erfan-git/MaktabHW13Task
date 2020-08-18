package com.example.maktabhw13task.controller.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.maktabhw13task.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerTaskDialog extends DialogFragment {

    public static final String ARG_DATE = "ArgDate";

    public static DatePickerTaskDialog newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerTaskDialog fragment = new DatePickerTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String EXTRA_USER_SELECTED_DATE = "com.example.maktabhw13task.controller.fragments.ExtraUserSelectedDate";
    private DatePicker mDatePicker;
    private Date mDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = (Date) getArguments().getSerializable(ARG_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_task_date_picker, null);
       findViews(view);

       setCurrentTime();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Date Picker")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.setTime(mDate);
                        gc.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtra(EXTRA_USER_SELECTED_DATE, gc));
                    }
                })
                .setNeutralButton(android.R.string.cancel,null)
                .setView(view)
                .create();
    }
    private void findViews(View view){
        mDatePicker = view.findViewById(R.id.datePicker);
    }

    private void setCurrentTime(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(mDate);
        mDatePicker.init(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), null);
    }
}