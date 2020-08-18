package com.example.maktabhw13task.controller.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ViewTaskDialog extends DialogFragment {

    public static ViewTaskDialog newInstance(TaskModel taskModel) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, taskModel);
        ViewTaskDialog fragment = new ViewTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }
    public static final String ARG_TASK = "ArgTabPosition";


    private TaskModel mCurrentTask;

    private Button  mButtonDatePicker, mButtonTimerPicker;
    private TextInputEditText mInputEditTextTitle, mInputEditTextDescription;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentTask = (TaskModel) getArguments().getSerializable(ARG_TASK);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view_task,null);

        findViews(view);

        initialViews();


        return new AlertDialog.Builder(getActivity())
                .setTitle("Task")
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    private void findViews(View view){

        mButtonDatePicker = view.findViewById(R.id.buttonDialogDatePicker);
        mButtonTimerPicker = view.findViewById(R.id.buttonDialogTimePicker);

        mInputEditTextTitle = view.findViewById(R.id.editTextDialogTitle);
        mInputEditTextDescription = view.findViewById(R.id.editTextDialogDescription);
    }

    private void initialViews(){

        mInputEditTextTitle.setText(mCurrentTask.getTitle());
        mInputEditTextDescription.setText(mCurrentTask.getDescription());

        mButtonTimerPicker.setText(android.text.format.DateFormat.format("HH:mm", mCurrentTask.getDate().getTime()));
        mButtonDatePicker.setText(android.text.format.DateFormat.format("MM/dd/yyyy", mCurrentTask.getDate().getTime()));
    }
}
