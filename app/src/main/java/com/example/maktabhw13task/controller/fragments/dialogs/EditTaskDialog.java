package com.example.maktabhw13task.controller.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.controller.activity.TaskViewPagerActivity;
import com.example.maktabhw13task.enums.TaskState;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditTaskDialog extends DialogFragment {

    public static final String TAG_DATE_PICKER = "datePickerTag";
    public static final int REQUEST_CODE_DATE_PICKER = 1;
    public static final int REQUEST_CODE_TIME_PICKER = 2;
    public static final String TAG_TIME_PICKER = "TagTimePicker";
    public static final String ARG_TAB_POSITION = "ArgTabPosition";

    public static EditTaskDialog newInstance(TaskModel taskModel) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TAB_POSITION, taskModel);
        EditTaskDialog fragment = new EditTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }
    UserRepository mUserRepository;
    TaskRepository mTaskRepository;

    private Spinner mSpinnerState;
    private Button mButtonSave, mButtonCancel, mButtonDatePicker, mButtonTimerPicker;
    private TextInputEditText mInputEditTextTitle, mInputEditTextDescription;

    private Calendar mCalendar;
    private TaskState mTaskState = TaskState.TODO;
    private GregorianCalendar mUnSavedDate;
    private GregorianCalendar mUnSavedTime;
    private TaskModel mTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance();
        mTaskRepository = TaskRepository.getInstance();
        mTask = (TaskModel) getArguments().getSerializable(ARG_TAB_POSITION);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_task,null);

        findViews(view);

        initialViews();

        setListeners();

        setSpinner();

        return new AlertDialog.Builder(getActivity())
                .setTitle("New Task")
                .setView(view)
                .create();
    }

    private void findViews(View view){
        mSpinnerState = view.findViewById(R.id.spinnerDialogState);
        mButtonDatePicker = view.findViewById(R.id.buttonDialogDatePicker);
        mButtonTimerPicker = view.findViewById(R.id.buttonDialogTimePicker);
        mButtonSave = view.findViewById(R.id.buttonDialogSave);
        mButtonCancel = view.findViewById(R.id.buttonDialogCancel);
        mInputEditTextTitle = view.findViewById(R.id.editTextDialogTitle);
        mInputEditTextDescription = view.findViewById(R.id.editTextDialogDescription);
    }

    private void initialViews(){

        mUnSavedTime = new GregorianCalendar();
        mUnSavedDate = new GregorianCalendar();
        mUnSavedDate.setTime(mTask.getDate());
        mUnSavedTime.setTime(mTask.getDate());
        mButtonTimerPicker.setText(android.text.format.DateFormat.format("HH:mm",mUnSavedTime.getTime()));
        mButtonDatePicker.setText(android.text.format.DateFormat.format("MM/dd/yyyy", mUnSavedDate.getTime()));
        mInputEditTextTitle.setText(mTask.getTitle());
        mInputEditTextDescription.setText(mTask.getDescription());
    }

    private void setSpinner() {
        {

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_item, android.R.layout.simple_spinner_dropdown_item);

            mSpinnerState.setAdapter(adapter);
            mSpinnerState.setSelection(getTabPosition());

            mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            mTaskState = TaskState.TODO;
                            break;
                        case 1:
                            mTaskState = TaskState.DOING;
                            break;
                        case 2:
                            mTaskState = TaskState.DONE;
                            break;
                        default:
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }

    private void setListeners(){
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerTaskDialog dateDialog = DatePickerTaskDialog.newInstance(mUnSavedDate.getTime());
                dateDialog.setTargetFragment(EditTaskDialog.this, REQUEST_CODE_DATE_PICKER);
                dateDialog.show(getFragmentManager(), TAG_DATE_PICKER);
            }
        });

        mButtonTimerPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerTaskDialog timeDialog = TimePickerTaskDialog.newInstance(mUnSavedTime.getTime());
                timeDialog.setTargetFragment(EditTaskDialog.this, REQUEST_CODE_TIME_PICKER);
                timeDialog.show(getFragmentManager(), TAG_TIME_PICKER);
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInputEditTextTitle.getText().toString().length() == 0){
                    mInputEditTextTitle.setError("Title Can not be empty.");
                    return;
                }

                mCalendar = Calendar.getInstance();
                mCalendar.set(mUnSavedDate.get(Calendar.YEAR), mUnSavedDate.get(Calendar.MONTH),mUnSavedDate.get(Calendar.DAY_OF_MONTH),mUnSavedTime.get(Calendar.HOUR_OF_DAY),mUnSavedTime.get(Calendar.MINUTE));

                mTaskRepository.getTask(mTaskRepository.getPosition(mTask.getTaskId())).setTitle(mInputEditTextTitle.getText().toString());
                mTaskRepository.getTask(mTaskRepository.getPosition(mTask.getTaskId())).setDescription(mInputEditTextDescription.getText().toString());
                mTaskRepository.getTask(mTaskRepository.getPosition(mTask.getTaskId())).setDate(mCalendar.getTime());
                mTaskRepository.getTask(mTaskRepository.getPosition(mTask.getTaskId())).setTaskState(mTaskState);


                ((TaskViewPagerActivity)getActivity()).updateRecyclerView();
                dismiss();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER){
            mUnSavedDate = (GregorianCalendar) data.getSerializableExtra(DatePickerTaskDialog.EXTRA_USER_SELECTED_DATE);
            mButtonDatePicker.setText(android.text.format.DateFormat.format("MM/dd/yyyy", mUnSavedDate.getTime()));
        }
        if (requestCode == REQUEST_CODE_TIME_PICKER){
            mUnSavedTime = (GregorianCalendar) data.getSerializableExtra(TimePickerTaskDialog.EXTRA_USER_SELECTED_TIME);
            mButtonTimerPicker.setText(android.text.format.DateFormat.format("HH:mm", mUnSavedTime.getTime()));
        }
    }

    private int getTabPosition(){
        if (mTask.getTaskState().equals(TaskState.TODO))
            return 0;
        if (mTask.getTaskState().equals(TaskState.DOING))
            return 1;
        else
            return 2;
    }

}
